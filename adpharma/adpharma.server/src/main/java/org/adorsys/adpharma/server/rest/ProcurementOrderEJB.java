package org.adorsys.adpharma.server.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem_;
import org.adorsys.adpharma.server.jpa.ProcurementOrderPreparationData;
import org.adorsys.adpharma.server.jpa.ProcurementOrderType;
import org.adorsys.adpharma.server.jpa.ProcurementOrder_;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.ProcurementOrderRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.PhmlOrderBuilder;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class ProcurementOrderEJB
{

	@Inject
	private ProcurementOrderRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ProcurementOrderItemMerger procurementOrderItemMerger;

	@Inject
	private SupplierMerger supplierMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private ProcurementOrderItemEJB procurementOrderItemEJB;

	@EJB
	private DeliveryItemEJB deliveryItemEJB;
	@EJB
	private SecurityUtil securityUtil;

	public ProcurementOrder create(ProcurementOrder entity)
	{
		ProcurementOrder save = repository.save(attach(entity));
		save.setProcurementOrderNumber(SequenceGenerator.PORCHASE_SEQUENCE_PREFIXE+save.getId());
		return repository.save(save);
	}

	public ProcurementOrder deleteById(Long id)
	{
		ProcurementOrder entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public ProcurementOrder update(ProcurementOrder entity)
	{
		return repository.save(attach(entity));
	}

	public ProcurementOrder findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<ProcurementOrder> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<ProcurementOrder> findBy(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, Object>[] attributes)
	{
		return repository.criteriafindBy(entity, attributes).orderDesc(ProcurementOrder_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countBy(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<ProcurementOrder> findByLike(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, Object>[] attributes)
	{
		return repository.criteriafindBy(entity, attributes).orderDesc(ProcurementOrder_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countByLike(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	@Inject
	private PhmlOrderBuilder orderBuilder;

	public ProcurementOrder sendOrderToPhmlServer(ProcurementOrder order){
		ProcurementOrder 	original = findById(order.getId());
		if(DocumentProcessingState.ONGOING.equals(order.getPoStatus())){
			original.setPoStatus(DocumentProcessingState.SENT);
			original = repository.save(original);
			try {
				orderBuilder.build(original);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}else {
			return original ;
		}
		return original ;
	}

	private ProcurementOrder attach(ProcurementOrder entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		// aggregated
		entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setVat(vATMerger.bindAggregated(entity.getVat()));

		// composed collections
		Set<ProcurementOrderItem> procurementOrderItems = entity.getProcurementOrderItems();
		for (ProcurementOrderItem procurementOrderItem : procurementOrderItems)
		{
			procurementOrderItem.setProcurementOrder(entity);
		}

		return entity;
	}
	
	

	@Inject
	private CustomerInvoiceItemEJB customerInvoiceItemEJB;

	public ProcurementOrder proccessPreparation(ProcurementOrderPreparationData data){
		Login user = securityUtil.getConnectedUser();
		ProcurementOrder procurementOrder = new ProcurementOrder();
		procurementOrder.setAgency(user.getAgency());
		procurementOrder.setCreatedDate(new Date());
		procurementOrder.setCreatingUser(user);
		procurementOrder.setPoStatus(DocumentProcessingState.ONGOING);
		procurementOrder.setProcurementOrderNumber(SequenceGenerator.getSequence(SequenceGenerator.PORCHASE_SEQUENCE_PREFIXE));
		procurementOrder.setProcurementOrderType(ProcurementOrderType.ORDINARY);
		procurementOrder.setProcmtOrderTriggerMode(data.getProcmtOrderTriggerMode());
		procurementOrder.setSupplier(data.getSupplier());
		List<CustomerInvoiceItem> customerInvoiceItems = customerInvoiceItemEJB.findPreparationDataItem(data);
		HashMap<Article, ProcurementOrderItem> cashedItem = new  HashMap<Article,ProcurementOrderItem>();
		for (CustomerInvoiceItem item : customerInvoiceItems) {
			BigDecimal totalPurchasePrice = BigDecimal.ZERO;
			if(cashedItem.containsKey(item.getArticle())){
				BigDecimal qtyOrdered = cashedItem.get(item.getArticle()).getQtyOrdered();
				cashedItem.get(item.getArticle()).setQtyOrdered(qtyOrdered.add(item.getPurchasedQty()));
				totalPurchasePrice = cashedItem.get(item.getArticle()).calculTotalPuschasePrice();
			}else {
				ProcurementOrderItem procurementOrderItem = new ProcurementOrderItem();
				procurementOrderItem.setArticle(item.getArticle());
				procurementOrderItem.setArticleName(item.getArticle().getArticleName());
				procurementOrderItem.setCreatingUser(user);
				procurementOrderItem.setMainPic(item.getArticle().getPic());
				procurementOrderItem.setPoStatus(DocumentProcessingState.ONGOING);
				procurementOrderItem.setProductRecCreated(new Date());
				procurementOrderItem.setPurchasePricePU(item.getPurchasePricePU()!=null?item.getPurchasePricePU():item.getSalesPricePU());
				procurementOrderItem.setQtyOrdered(item.getPurchasedQty());
				procurementOrderItem.setSalesPricePU(item.getSalesPricePU());
				procurementOrderItem.setSecondaryPic(item.getArticle().getPic());
				procurementOrderItem.setStockQuantity(item.getArticle().getQtyInStock());
				procurementOrderItem.setValid(Boolean.FALSE);
				procurementOrderItem.setAvailableQty( item.getPurchasedQty());
				totalPurchasePrice = procurementOrderItem.calculTotalPuschasePrice();
				cashedItem.put(item.getArticle(), procurementOrderItem);
			}
			procurementOrder.setAmountAfterTax(procurementOrder.getAmountAfterTax().add(totalPurchasePrice));
			BigDecimal vatRateRaw = item.getArticle().getVat()==null?BigDecimal.ZERO:VAT.getRawRate(item.getArticle().getVat().getRate());
			BigDecimal purchasePriceBeforTax = totalPurchasePrice.divide(BigDecimal.ONE.add(vatRateRaw), 4, RoundingMode.HALF_EVEN);
			// Amount before tax
			procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().add(purchasePriceBeforTax));
			// Amount vat
			procurementOrder.setTaxAmount(procurementOrder.getTaxAmount().add(totalPurchasePrice.subtract(purchasePriceBeforTax)));
		}
		procurementOrder.setNetAmountToPay(procurementOrder.getAmountAfterTax().subtract(procurementOrder.getAmountDiscount()));

		Collection<ProcurementOrderItem> procurementOrderItems = cashedItem.values();
		procurementOrder.getProcurementOrderItems().addAll(procurementOrderItems);
		return procurementOrder = create(procurementOrder);
	}

	public void handleStockMovementEvent(@Observes @DocumentProcessedEvent StockMovement stockMovement){
		if(stockMovement.canEnablePurchase()){
			//			Article article = stockMovement.getArticle();
			//			ProcurementOrderItem searchEntity = new ProcurementOrderItem();
			//			searchEntity.setArticle(article);
			//			DeliveryItem deliveryItem = new DeliveryItem();
			//			deliveryItem.setInternalPic(stockMovement.getInternalPic());
			//			deliveryItem.setArticle(stockMovement.getArticle());
			//			List<DeliveryItem> foundDI = deliveryItemEJB.findBy(deliveryItem, 0, 1, new SingularAttribute[]{DeliveryItem_.internalPic, DeliveryItem_.article});
			//			if(foundDI==null) throw new IllegalStateException("Deliveryitem associated with this stock movement not found.");
			//			deliveryItem = foundDI.iterator().next();
			//			List<ProcurementOrderItem> found = procurementOrderItemEJB.findBy(searchEntity, 0, 1, new SingularAttribute[]{ProcurementOrderItem_.article, ProcurementOrderItem_.poStatus});
			//			ProcurementOrderItem procurementOrderItem = null;
			//			if(found.isEmpty()){
			//				Login user = securityUtil.getConnectedUser();
			//				Agency agency = user.getAgency();
			//				procurementOrderItem = new ProcurementOrderItem();
			//				procurementOrderItem.setArticle(article);
			//				procurementOrderItem.setArticleName(deliveryItem.getArticleName());
			//				procurementOrderItem.setCreatingUser(user);
			//				procurementOrderItem.setMainPic(article.getPic());
			//				procurementOrderItem.setPoStatus(DocumentProcessingState.ONGOING);
			//				procurementOrderItem.setProductRecCreated(new Date());
			//				procurementOrderItem.setPurchasePricePU(deliveryItem.getPurchasePricePU());
			//				procurementOrderItem.setQtyOrdered(BigDecimal.ZERO);
			//				procurementOrderItem.setSalesPricePU(deliveryItem.getSalesPricePU());
			//				procurementOrderItem.setSecondaryPic(deliveryItem.getSecondaryPic());
			//				procurementOrderItem.setStockQuantity(article.getQtyInStock());
			//				procurementOrderItem.setValid(Boolean.FALSE);
			//				ProcurementOrder procurementOrder = new ProcurementOrder();
			//				procurementOrder.setPoStatus(DocumentProcessingState.ONGOING);
			//				Supplier supplier = deliveryItem.getDelivery().getSupplier();
			//				procurementOrder.setSupplier(supplier);
			//				List<ProcurementOrder> foundPOs = findBy(procurementOrder, 0, 1, new SingularAttribute[]{ProcurementOrder_.poStatus,ProcurementOrder_.supplier});
			//				if(!foundPOs.isEmpty()){
			//					procurementOrder = foundPOs.iterator().next();
			//				} else {
			//					procurementOrder = new ProcurementOrder();
			//					procurementOrder.setAgency(agency);
			//					procurementOrder.setCreatedDate(new Date());
			//					procurementOrder.setCreatingUser(user);
			//					procurementOrder.setPoStatus(DocumentProcessingState.ONGOING);
			//					procurementOrder.setProcurementOrderNumber(SequenceGenerator.getSequence(SequenceGenerator.PORCHASE_SEQUENCE_PREFIXE));
			//					procurementOrder.setProcurementOrderType(ProcurementOrderType.ORDINARY);
			//					procurementOrder.setSupplier(supplier);
			//					procurementOrder = create(procurementOrder);
			//				}
			//				procurementOrderItem.setProcurementOrder(procurementOrder);
			//				procurementOrderItem = procurementOrderItemEJB.create(procurementOrderItem);
			//			} else {
			//				procurementOrderItem = found.iterator().next();
			//			}
			//
			//			BigDecimal diffAmountAfterTax = null;
			//			if(stockMovement.isReturnStockMovement()){
			//				procurementOrderItem.setQtyOrdered(procurementOrderItem.getQtyOrdered().subtract(stockMovement.getMovedQty()));
			//				diffAmountAfterTax = BigDecimal.ZERO.subtract(stockMovement.getMovedQty()).multiply(procurementOrderItem.getPurchasePricePU());
			//			}else {
			//				procurementOrderItem.setQtyOrdered(procurementOrderItem.getQtyOrdered().add(stockMovement.getMovedQty()));
			//				diffAmountAfterTax = stockMovement.getMovedQty().multiply(procurementOrderItem.getPurchasePricePU());
			//			}
			//			procurementOrderItem.setTotalPurchasePrice(procurementOrderItem.getQtyOrdered().multiply(procurementOrderItem.getPurchasePricePU()));
			//			procurementOrderItem = procurementOrderItemEJB.update(procurementOrderItem);
			//
			//			ProcurementOrder procurementOrder = procurementOrderItem.getProcurementOrder();
			//			procurementOrder.setAmountAfterTax(procurementOrder.getAmountAfterTax().add(diffAmountAfterTax));
			//			BigDecimal vatRateRaw = procurementOrderItem.getArticle().getVat()==null?BigDecimal.ZERO:VAT.getRawRate(procurementOrderItem.getArticle().getVat().getRate());
			//			BigDecimal diffAmountBeforeTax = diffAmountAfterTax.divide(BigDecimal.ONE.add(vatRateRaw), 4, RoundingMode.HALF_EVEN);
			//			procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().add(diffAmountBeforeTax));
			//			BigDecimal difAmountVat = diffAmountAfterTax.subtract(diffAmountBeforeTax);
			//			procurementOrder.setTaxAmount(procurementOrder.getTaxAmount().add(difAmountVat));
			//			procurementOrder.setNetAmountToPay(procurementOrder.getAmountAfterTax().subtract(procurementOrder.getAmountDiscount()));
			//			update(procurementOrder);
		}
	}
}
