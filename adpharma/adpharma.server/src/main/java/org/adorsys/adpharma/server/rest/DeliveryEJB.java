package org.adorsys.adpharma.server.rest;

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
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

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

		Delivery save = repository.save(attach(entity));
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
			deliveryItem = deliveryItemEJB.update(deliveryItem);
		}
		delivery.setDeliveryProcessingState(DocumentProcessingState.CLOSED);
		delivery.computeAmount();
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

	public List<Delivery> findBy(Delivery entity, int start, int max, SingularAttribute<Delivery, ?>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.findBy(delivery, start, max, attributes);
	}

	public Long countBy(Delivery entity, SingularAttribute<Delivery, ?>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.count(delivery, attributes);
	}

	public List<Delivery> findByLike(Delivery entity, int start, int max, SingularAttribute<Delivery, ?>[] attributes)
	{
		Delivery delivery = attach(entity);
		return repository.findByLike(delivery, start, max, attributes);
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
