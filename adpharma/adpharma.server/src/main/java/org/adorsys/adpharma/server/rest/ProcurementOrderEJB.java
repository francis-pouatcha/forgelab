package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.StockMovementTerminal;
import org.adorsys.adpharma.server.jpa.StockMovementType;
import org.adorsys.adpharma.server.repo.ProcurementOrderRepository;

import java.util.Set;

import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;

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

   public ProcurementOrder create(ProcurementOrder entity)
   {
      return repository.save(attach(entity));
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

   public List<ProcurementOrder> findBy(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ProcurementOrder> findByLike(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
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
   
   public void handleStockMovementEvent(@Observes @DocumentProcessedEvent StockMovement stockMovement){
	   StockMovementTerminal movementOrigin = stockMovement.getMovementOrigin();
	   StockMovementTerminal movementDestination = stockMovement.getMovementDestination();
	   StockMovementType movementType = stockMovement.getMovementType();
	   // Sales
	   if(StockMovementType.OUT.equals(movementType) && 
			   StockMovementTerminal.WAREHOUSE.equals(movementOrigin) && 
			   StockMovementTerminal.CUSTOMER.equals(movementDestination))
		   handleSupplierIncreaseOrder(stockMovement);
	   
	   // Expired
	   if(StockMovementType.OUT.equals(movementType) && 
			   StockMovementTerminal.WAREHOUSE.equals(movementOrigin) && 
			   StockMovementTerminal.TRASH.equals(movementDestination))
		   handleSupplierIncreaseOrder(stockMovement);
	   
	   // return or canceled sales.
	   if(StockMovementType.IN.equals(movementType) && 
			   StockMovementTerminal.CUSTOMER.equals(movementOrigin) && 
			   StockMovementTerminal.WAREHOUSE.equals(movementDestination))
		   handleSupplierReduceOrder(stockMovement);
   }

	private void handleSupplierReduceOrder(StockMovement stockMovement) {
	// TODO Auto-generated method stub
	
	}

	private void handleSupplierIncreaseOrder(StockMovement stockMovement) {
//		Article article = stockMovement.getArticle();
//		ProcurementOrder procurementOrder = new ProcurementOrder();
//		
//		ProcurementOrderItem procurementOrderItem = new ProcurementOrderItem();
//		procurementOrderItem.s
//		new Pu
		
	}
}
