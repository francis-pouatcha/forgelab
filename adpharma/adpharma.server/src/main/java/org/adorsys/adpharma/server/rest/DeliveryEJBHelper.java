package org.adorsys.adpharma.server.rest;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.repo.DeliveryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

@Stateless
public class DeliveryEJBHelper
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
	private AgencyMerger agencyMerger;

	@EJB
	private SecurityUtil securityUtil;

	@Inject
	@DocumentClosedEvent
	private Event<Delivery> deliveryClosedDoneEvent;

	public Delivery update(Delivery entity)
	{
		return repository.save(attach(entity));
	}

	public Delivery saveAndClose(Delivery delivery) {
		Delivery closedDelivery = update(delivery);
		deliveryClosedDoneEvent.fire(closedDelivery);
		return closedDelivery;
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
