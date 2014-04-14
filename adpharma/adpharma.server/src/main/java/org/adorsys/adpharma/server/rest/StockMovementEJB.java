package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.StockMovementTerminal;
import org.adorsys.adpharma.server.jpa.StockMovementType;
import org.adorsys.adpharma.server.repo.StockMovementRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

@Stateless
public class StockMovementEJB
{

   @Inject
   private StockMovementRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private SecurityUtil securityUtil;
   
   @Inject
   @DocumentProcessedEvent
   private Event<StockMovement> stockMovementEvent;
   
   public StockMovement create(StockMovement entity)
   {
      return repository.save(attach(entity));
   }

   public StockMovement deleteById(Long id)
   {
      StockMovement entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StockMovement update(StockMovement entity)
   {
      return repository.save(attach(entity));
   }

   public StockMovement findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<StockMovement> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StockMovement> findBy(StockMovement entity, int start, int max, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StockMovement> findByLike(StockMovement entity, int start, int max, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StockMovement attach(StockMovement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
   
   public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();

		// Generate Stock Movement for each delivery item
		for (DeliveryItem deliveryItem : deliveryItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(creatingUser.getAgency());
			sm.setInternalPic(deliveryItem.getInternalPic());
			sm.setMovementType(StockMovementType.IN);
			sm.setArticle(deliveryItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setInitialQty(BigDecimal.ZERO);
			sm.setMovedQty(deliveryItem.getStockQuantity());
			sm.setFinalQty(deliveryItem.getStockQuantity());
			sm.setMovementOrigin(StockMovementTerminal.SUPPLIER);
			sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			sm.setOriginatedDocNumber(closedDelivery.getDeliveryNumber());
			sm.setTotalPurchasingPrice(deliveryItem.getTotalPurchasePrice());
			if(deliveryItem.getSalesPricePU()!=null && deliveryItem.getStockQuantity()!=null)
				sm.setTotalSalesPrice(deliveryItem.getSalesPricePU().multiply(deliveryItem.getStockQuantity()));
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}

   /**
    * Create corresponding stock movement.
    * 
    * @param salesOrder
    */
   public void handleSalesClosed(@Observes @DirectSalesClosedEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(salesOrder.getAgency());
			sm.setInternalPic(salesOrderItem.getInternalPic());
			sm.setMovementType(StockMovementType.OUT);
			sm.setArticle(salesOrderItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setInitialQty(BigDecimal.ZERO);//supposed to be qty in stock
			BigDecimal releasedQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
			BigDecimal movedQty = releasedQty.subtract(returnedQty);
			sm.setMovedQty(movedQty);
			sm.setFinalQty(movedQty);//supposed to be qty in stock.
			
			if(releasedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.CUSTOMER);
			} else if (returnedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.CUSTOMER);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			} else {
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			}

			sm.setOriginatedDocNumber(salesOrder.getSoNumber());
			sm.setTotalSalesPrice(salesOrder.getAmountBeforeTax());
			sm.setTotalPurchasingPrice(BigDecimal.ZERO);//TODO ADD purchase price field on salesorder item
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}
   
   /**
    * Sales order canceled, create corresponding stock movement.
    * 
    * Canceling will not delete the original stock movement, but create a new one
    * to neutralize the effect of the old one.
    * 
    * @param salesOrder
   public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(salesOrder.getAgency());
			sm.setInternalPic(salesOrderItem.getInternalPic());
			sm.setMovementType(StockMovementType.IN);// return article in the stock.
			sm.setArticle(salesOrderItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setInitialQty(BigDecimal.ZERO);//supposed to be qty in stock
			BigDecimal releasedQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
			BigDecimal movedQty = returnedQty.subtract(releasedQty);// Return minus release. inverse of sales operation.
			sm.setMovedQty(movedQty);
			sm.setFinalQty(movedQty);//supposed to be qty in stock.
			
			if(releasedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.CUSTOMER);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			} else if (returnedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.CUSTOMER);
			} else {
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			}

			sm.setOriginatedDocNumber(salesOrder.getSoNumber());
			sm.setTotalSalesPrice(salesOrder.getAmountBeforeTax());
			sm.setTotalPurchasingPrice(BigDecimal.ZERO);//TODO ADD purchase price field on sales order item
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}
    */
}
