package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedDoneEvent;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
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
   private SecurityUtil securityUtil ;
   
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
   
   public void generateDeliveryStockMouvements(@Observes @DocumentClosedDoneEvent Delivery closedDelivery){
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
		}
	}

}
