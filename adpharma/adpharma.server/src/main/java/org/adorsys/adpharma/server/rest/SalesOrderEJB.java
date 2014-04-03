package org.adorsys.adpharma.server.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedDoneEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

import java.util.Set;

import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class SalesOrderEJB
{

   @Inject
   private SalesOrderRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private InsurranceMerger insurranceMerger;

   @Inject
   private SalesOrderItemMerger salesOrderItemMerger;

   @Inject
   private AgencyMerger agencyMerger;

	@EJB
	private SecurityUtil securityUtil;
	
	@EJB
	private SalesOrderItemEJB salesOrderItemEJB;

	@Inject
	@DocumentClosedDoneEvent
	private Event<SalesOrder> salesOrderClosedDoneEvent;
   
   public SalesOrder create(SalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SalesOrder deleteById(Long id)
   {
      SalesOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SalesOrder update(SalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SalesOrder findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SalesOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SalesOrder> findBy(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SalesOrder> findByLike(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SalesOrder attach(SalesOrder entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

      // aggregated
      entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

      // aggregated
      entity.setInsurance(insurranceMerger.bindAggregated(entity.getInsurance()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      // aggregated
      entity.setSalesAgent(loginMerger.bindAggregated(entity.getSalesAgent()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // composed collections
      Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
      for (SalesOrderItem salesOrderItem : salesOrderItems)
      {
         salesOrderItem.setSalesOrder(entity);
      }

      return entity;
   }
	
	public SalesOrder saveAndClose(SalesOrder salesOrder) {
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			salesOrderItem.setRecordDate(new Date());
			salesOrderItem = salesOrderItemEJB.update(salesOrderItem);
		}
		salesOrder.setCreationDate(creationDate);
		salesOrder.setSalesAgent(creatingUser);
		salesOrder.setSalesOrderStatus(DocumentProcessingState.CLOSED);
		SalesOrder closedSales = update(salesOrder);
		salesOrderClosedDoneEvent.fire(closedSales);
		return closedSales;
	}
}
