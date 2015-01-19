package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.ProcessTransferRequestEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.Delivery_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.ArticleRepository;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;
import org.adorsys.adpharma.server.utils.DeliveryFromOrderData;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class DeliveryEJB
{

	@Inject
	private DeliveryRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private SupplierMerger supplierMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private CurrencyMerger currencyMerger;

	@Inject
	private DeliveryItemMerger deliveryItemMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private DeliveryItemEJB deliveryItemEJB;

	@Inject
	private ArticleEJB articleEJB;

	@Inject
	private ArticleLotEJB articleLotEJB;

	@EJB
	private SecurityUtil securityUtil;

	@Inject
	@DocumentClosedEvent
	private Event<Delivery> deliveryClosedDoneEvent;

	@Inject
	private ApplicationConfiguration applicationConfiguration;
	
	@Inject
	private DeliveryEJBHelper deliveryEJBHelper;

	public Delivery create(Delivery entity)
	{

		String deliveryNumber = entity.getDeliveryNumber();
		Delivery save = repository.save(attach(entity));
		if(StringUtils.isBlank(deliveryNumber))
			save.setDeliveryNumber(SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE+save.getId());
		return repository.save(save);
	}

	public Delivery deleteById(Long id)
	{
		Delivery entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public Delivery update(Delivery entity)
	{
		return repository.save(attach(entity));
	}

	/**
	 * Suspend transation and proceed iteratively.
	 * 
	 * @param delivery
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Delivery saveAndClose(Delivery delivery) {
		delivery = attach(delivery);
		Login creatingUser = securityUtil.getConnectedUser();
		Set<DeliveryItem> deliveryItems = delivery.getDeliveryItems();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");

		// First transaction. Create last delivery items delivered with this request.
		for (DeliveryItem deliveryItem : deliveryItems) {
			deliveryItem = deliveryItemEJB.findById(deliveryItem.getId());
			Long sectionId = deliveryItem.getArticle().getSection().getId();
			String internalPic = articleLotEJB.newLotNumber(deliveryItem.getMainPic());
			if(!isManagedLot) internalPic=internalPic+sectionId ;
			deliveryItem.setInternalPic(internalPic);
			deliveryItem.setCreatingUser(creatingUser);
			if(deliveryItem.getId()==null){
				deliveryItemEJB.create(deliveryItem);
			} else {
				deliveryItem = deliveryItemEJB.update(deliveryItem);
			}
		}

		// Init fields
		delivery.setAmountAfterTax(BigDecimal.ZERO);
		delivery.setAmountBeforeTax(BigDecimal.ZERO);
		delivery.setAmountVat(BigDecimal.ZERO);
		delivery.setNetAmountToPay(BigDecimal.ZERO);
		if(delivery.getAmountDiscount()==null)delivery.setAmountDiscount(BigDecimal.ZERO);

		// navigate over all delivery items
		// Francis 01/15/2015: this is where we kill everything. We will have to proceed per block of
		// 100 items to avoid overloading the transaction.
		DeliveryItem searchInput = new DeliveryItem();
		SingularAttribute[] attributes = new SingularAttribute[]{DeliveryItem_.delivery};
		Long count = deliveryItemEJB.countBy(searchInput, attributes);
		int start = 0;
		int max = 100;

//		List<DeliveryItem> deliveryItems2 = deliveryItemEJB.findByDelivery(delivery);
//		delivery.getDeliveryItems().addAll(deliveryItems2);
		delivery.getDeliveryItems().clear();
		// Francis 01/15/2015
		// Very important do not do any update in this while loop. DO not change anything 
		// on the delivery item. This will cause a too big transaction.
		while(start<=count){
			List<DeliveryItem> deliveryItems2 = deliveryItemEJB.findBy(searchInput, start , max, attributes);
			start +=max;

			for (DeliveryItem deliveryItem : deliveryItems2) {
				BigDecimal totalPurchasePrice = deliveryItem.getTotalPurchasePrice();
				// Ammount after Tax
				delivery.setAmountAfterTax(delivery.getAmountAfterTax().add(totalPurchasePrice));
				VAT vat = deliveryItem.getArticle().getVat();
				BigDecimal vatRate = BigDecimal.ZERO;
				if(vat!=null)
					vatRate =vat.getRate();
				BigDecimal purchasePriceBeforTax = totalPurchasePrice.divide(BigDecimal.ONE.add(VAT.getRawRate(vatRate)), 4, RoundingMode.HALF_EVEN);
				// Amount before tax
				delivery.setAmountBeforeTax(delivery.getAmountBeforeTax().add(purchasePriceBeforTax));
				// Amount vat
				delivery.setAmountVat(delivery.getAmountVat().add(totalPurchasePrice.subtract(purchasePriceBeforTax)));
			}
		}

		delivery.setNetAmountToPay(delivery.getAmountAfterTax().subtract(delivery.getAmountDiscount()));
		delivery.setDeliveryProcessingState(DocumentProcessingState.CLOSED);
        delivery.setCloseUser(creatingUser.getLoginName());
        
        return deliveryEJBHelper.saveAndClose(delivery);
	}

	@Inject
	private ArticleRepository articleRepository ;

	public void handleDeliveryCreationForTransfer(@Observes @ProcessTransferRequestEvent ArticleLotTransferManager lotTransferManager ){
		ArticleLot lotToTransfer = lotTransferManager.getLotToTransfer();
		BigDecimal qtyToTransfer = lotTransferManager.getQtyToTransfer();
		lotTransferManager.getTargetSection() ;
		DeliveryItem deliveryItem = new DeliveryItem();
		deliveryItem.setArticle(lotToTransfer.getArticle());
		deliveryItem.setMainPic(lotToTransfer.getMainPic());
		List<DeliveryItem> found = deliveryItemEJB.findBy(deliveryItem, 0, 1, new SingularAttribute[]{DeliveryItem_.mainPic,DeliveryItem_.article});
		if(found.isEmpty()) throw new RuntimeException("Delivery Item not found") ;
		DeliveryItem sourceDi = found.iterator().next() ;
		Delivery sourceD = sourceDi.getDelivery();
		DeliveryItem newItem = new DeliveryItem();
		Delivery delivery = new Delivery();
		try {
			BeanUtils.copyProperties(delivery, sourceD);
			BeanUtils.copyProperties(newItem, sourceDi);

		} catch (Exception e) {
			throw new RuntimeException("Impossible to copy fileds") ;
		}
		List<Article> articles = articleRepository.findBySectionAndPic(lotToTransfer.getMainPic() ,lotTransferManager.getTargetSection());

		delivery.setId(null);
		delivery.setDeliveryNumber(null);
		delivery.setDeliveryItems(new HashSet<DeliveryItem>());
		delivery = create(delivery);
		newItem.setDelivery(delivery);
		newItem.setId(null);
		newItem.setQtyOrdered(qtyToTransfer);
		newItem.setArticle(articles.iterator().next());
		newItem.setStockQuantity(qtyToTransfer);
		newItem = deliveryItemEJB.create(newItem);
		delivery.getDeliveryItems().add(newItem);
		saveAndClose(delivery);

	}

	@Inject
	private ProcurementOrderEJB procurementOrderEJB ;

	public DeliveryFromOrderData deliveryFromProcurementOrder(ProcurementOrder order){
		order = procurementOrderEJB.findById(order.getId());
		if(DocumentProcessingState.CLOSED.equals(order.getPoStatus()))
			throw new IllegalStateException(" procurement order are already closed ? ") ;
		Login login = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Delivery delivery = new Delivery();
		delivery.setSupplier(order.getSupplier());
		delivery.setReceivingAgency(login.getAgency());
		delivery.setDeliverySlipNumber("From Import");
		delivery.setCreatingUser(login);
		delivery.setProcurementOrderNumber(order.getProcurementOrderNumber());
		delivery = create(delivery);

		Set<ProcurementOrderItem> items = order.getProcurementOrderItems();
		Set<DeliveryItem> deliveryItems= new HashSet<DeliveryItem>();
		BigDecimal amountHt = BigDecimal.ZERO;
		for (ProcurementOrderItem item : items) {
			if(BigDecimal.ZERO.compareTo(item.getAvailableQty())!=0){
				DeliveryItem deliveryItem = new DeliveryItem();
				deliveryItem.setArticle(item.getArticle());
				deliveryItem.setArticleName(item.getArticleName());
				deliveryItem.setAvailableQty(item.getAvailableQty());
				deliveryItem.setCreatingUser(login);
				deliveryItem.setCreationDate(creationDate);
				deliveryItem.setDelivery(delivery);
				deliveryItem.setExpirationDate(DateUtils.addYears(creationDate, 1));
				deliveryItem.setFreeQuantity(BigDecimal.ZERO);
				deliveryItem.setInternalPic(item.getMainPic());
				deliveryItem.setSecondaryPic(item.getSecondaryPic());
				deliveryItem.setMainPic(item.getMainPic());
				deliveryItem.setPurchasePricePU(item.getPurchasePricePU());
				deliveryItem.setQtyOrdered(item.getQtyOrdered());
				deliveryItem.setSalesPricePU(item.getSalesPricePU());
				deliveryItem.setStockQuantity(item.getAvailableQty());
				DeliveryItem create = deliveryItemEJB.create(deliveryItem);
				amountHt = amountHt.add(deliveryItem.getTotalPurchasePrice());
				deliveryItems.add(create);
			}
		}
		
		order.setPoStatus(DocumentProcessingState.CLOSED);
		// Add Close user
		order.setCloseUser(login.getLoginName());
		order = procurementOrderEJB.update(order);
		delivery.setAmountAfterTax(amountHt);
		delivery.setAmountBeforeTax(amountHt);
		delivery.setAmountVat(BigDecimal.ZERO);
		delivery.setAmountDiscount(BigDecimal.ZERO);
		delivery.setNetAmountToPay(amountHt);
		delivery.setDateOnDeliverySlip(creationDate);
		delivery.setDeliveryDate(creationDate);
		delivery.setDeliveryItems(deliveryItems);
		delivery.setDeliveryProcessingState(DocumentProcessingState.ONGOING);
		delivery= repository.save(delivery);
		return new DeliveryFromOrderData(delivery,order );
	}




	public Delivery findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<Delivery> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<Delivery> findBy(Delivery entity, int start, int max, SingularAttribute<Delivery, Object>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.criteriafindBy(delivery, attributes).orderDesc(Delivery_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();

	}

	public Long countBy(Delivery entity, SingularAttribute<Delivery, ?>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.count(delivery, attributes);
	}

	public List<Delivery> findByLike(Delivery entity, int start, int max, SingularAttribute<Delivery, Object>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.criteriafindBy(delivery, attributes).orderDesc(Delivery_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countByLike(Delivery entity, SingularAttribute<Delivery, ?>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.countLike(delivery, attributes);
	}

	private Delivery attach(Delivery entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		// aggregated
		entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

		// aggregated
		entity.setVat(vATMerger.bindAggregated(entity.getVat()));

		// aggregated
		entity.setCurrency(currencyMerger.bindAggregated(entity.getCurrency()));

		// aggregated
		entity.setReceivingAgency(agencyMerger.bindAggregated(entity.getReceivingAgency()));

		// composed collections
		Set<DeliveryItem> deliveryItems = entity.getDeliveryItems();
		for (DeliveryItem deliveryItem : deliveryItems)
		{
			deliveryItem.setDelivery(entity);
		}

		return entity;
	}
}
