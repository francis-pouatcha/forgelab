package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Delivery_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

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

	public Delivery saveAndClose(Delivery delivery) {
		delivery = attach(delivery);
		Login creatingUser = securityUtil.getConnectedUser();
		Set<DeliveryItem> deliveryItems = delivery.getDeliveryItems();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");

		for (DeliveryItem deliveryItem : deliveryItems) {
			String internalPic = deliveryItem.getMainPic();
			if(isManagedLot){
				internalPic = articleLotEJB.newLotNumber(deliveryItem.getMainPic());
			}
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
		List<DeliveryItem> deliveryItems2 = deliveryItemEJB.findByDelivery(delivery);
		delivery.getDeliveryItems().clear();
		delivery.getDeliveryItems().addAll(deliveryItems2);

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

		delivery.setNetAmountToPay(delivery.getAmountAfterTax().subtract(delivery.getAmountDiscount()));
		delivery.setDeliveryProcessingState(DocumentProcessingState.CLOSED);

		Delivery closedDelivery = update(delivery);
		deliveryClosedDoneEvent.fire(closedDelivery);
		return closedDelivery;
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
