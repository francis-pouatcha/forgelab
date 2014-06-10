package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotDetailsManager;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.StockMovementTerminal;
import org.adorsys.adpharma.server.jpa.StockMovementType;
import org.adorsys.adpharma.server.repo.ArticleRepository;

@Stateless
public class ArticleEJB
{

   @Inject
   private ArticleRepository repository;

   @Inject
   private ProductFamilyMerger productFamilyMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private SalesMarginMerger salesMarginMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private PackagingModeMerger packagingModeMerger;

   @Inject
   private SectionMerger sectionMerger;

   @Inject
   private ClearanceConfigMerger clearanceConfigMerger;

   public Article create(Article entity)
   {
      return repository.save(attach(entity));
   }

   public Article deleteById(Long id)
   {
      Article entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Article update(Article entity)
   {
      return repository.save(attach(entity));
   }

   public Article findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Article> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Article> findBy(Article entity, int start, int max, SingularAttribute<Article, ?>[] attributes)
   {
	   Article article = attach(entity);
      return repository.findBy(article, start, max, attributes);
   }

   public Long countBy(Article entity, SingularAttribute<Article, ?>[] attributes)
   {
	   Article article = attach(entity);
      return repository.count(article, attributes);
   }

   public List<Article> findByLike(Article entity, int start, int max, SingularAttribute<Article, ?>[] attributes)
   {
	   Article article = attach(entity);
      return repository.findByLike(article, start, max, attributes);
   }

   public Long countByLike(Article entity, SingularAttribute<Article, ?>[] attributes)
   {
	   Article article = attach(entity);
      return repository.countLike(article, attributes);
   }

   private Article attach(Article entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSection(sectionMerger.bindAggregated(entity.getSection()));

      // aggregated
      entity.setFamily(productFamilyMerger.bindAggregated(entity.getFamily()));

      // aggregated
      entity.setDefaultSalesMargin(salesMarginMerger.bindAggregated(entity.getDefaultSalesMargin()));

      // aggregated
      entity.setPackagingMode(packagingModeMerger.bindAggregated(entity.getPackagingMode()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setClearanceConfig(clearanceConfigMerger.bindAggregated(entity.getClearanceConfig()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      return entity;
   }

	/**
	 * Process a completed delivery.
	 * 	- 
	 * @param closedDelivery
	 */
	public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery){
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();

		// generate Article lot for each delivery item
		for (DeliveryItem deliveryItem : deliveryItems) {
			Article article = deliveryItem.getArticle();
			BigDecimal currenQtyInStock = article.getQtyInStock()==null?BigDecimal.ZERO:article.getQtyInStock();
			BigDecimal enteringQty = deliveryItem.getStockQuantity()==null?BigDecimal.ZERO:deliveryItem.getStockQuantity();

			BigDecimal qtyInStock = currenQtyInStock.add(enteringQty);
			article.setQtyInStock(qtyInStock);
			article.setLastStockEntry(new Date());

			BigDecimal currentPppu = article.getPppu()==null?BigDecimal.ZERO:article.getPppu();
			BigDecimal enteringPricePU = deliveryItem.getPurchasePricePU()==null?BigDecimal.ZERO:deliveryItem.getPurchasePricePU();

			// average pppu
			//			BigDecimal newPppu = currenQtyInStock.multiply(currentPppu).add(enteringQty.multiply(enteringPricePU)).divide(qtyInStock);
			article.setPppu(enteringPricePU); // just use last Price because qtyInstock could be zero

			BigDecimal currentSppu = article.getSppu()==null?BigDecimal.ZERO:article.getSppu();
			BigDecimal enteringSppu = deliveryItem.getSalesPricePU()==null?BigDecimal.ZERO:deliveryItem.getSalesPricePU();
			//			BigDecimal newSppu = currenQtyInStock.multiply(currentSppu).add(enteringQty.multiply(enteringSppu)).divide(qtyInStock);
			article.setSppu(enteringSppu); // just use last Price because qtyInstock could be zero

			article.setTotalStockPrice(qtyInStock.multiply(enteringSppu));

			article.setRecordingDate(new Date());

			update(article);
		}
	}

	/**
	 * Process a completed sales. Update the quantity of this article in stock.
	 * 	- 
	 * @param closedDelivery
	 */
	public void handleSalesClosed(@Observes @DirectSalesClosedEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			Article article = salesOrderItem.getArticle();
			BigDecimal currenQtyInStock = article.getQtyInStock()==null?BigDecimal.ZERO:article.getQtyInStock();
			BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

			BigDecimal qtyInStock = currenQtyInStock.subtract(releasingQty).add(returnedQty);
			article.setQtyInStock(qtyInStock);
			article.setLastOutOfStock(new Date());

			article.setTotalStockPrice(qtyInStock.multiply(article.getSppu()));

			article.setRecordingDate(new Date());

			update(article);
		}
	}
	
	public void handleArticleLotDetails(@Observes @DocumentProcessedEvent ArticleLotDetailsManager  lotDetailsManager){
		Article source = lotDetailsManager.getDetailConfig().getSource();
		 Article target = lotDetailsManager.getDetailConfig().getTarget();
		lotDetailsManager.getLotToDetails();
		if(lotDetailsManager==null)
			return;
	    if(source == null || target == null)
	    	throw new IllegalStateException("source and target  article is required !");
	    source =   findById(source.getId());
	    target =   findById(target.getId());
	    source.setQtyInStock(source.getQtyInStock().subtract(lotDetailsManager.getDetailsQty()));
	    target.setQtyInStock(target.getQtyInStock().add(lotDetailsManager.getDetailsQty().multiply(lotDetailsManager.getDetailConfig().getTargetQuantity())));
	    update(source);
	    update(target);
	}
	
	/**
	 * update article stocks according to returned qty.
	 * 
	 * @param salesOrder
	 */
	public void handleReturnSales(@Observes @ReturnSalesEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			if(salesOrderItem.hasReturnArticle()){
				Article article = salesOrderItem.getArticle();
				BigDecimal movedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
				article.setQtyInStock(article.getQtyInStock().add(movedQty));
				update(article);
			}

		}
	}

	/**
	 * Reset the stock quantity in case this sales has been canceled.
	 * 
	 * @param salesOrder
  public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
	   Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

	   for (SalesOrderItem salesOrderItem : salesOrderItems) {
		   Article article = salesOrderItem.getArticle();
		   BigDecimal currenQtyInStock = article.getQtyInStock()==null?BigDecimal.ZERO:article.getQtyInStock();
		   BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
		   BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

		   BigDecimal qtyInStock = currenQtyInStock.add(releasingQty).subtract(returnedQty);
		   article.setQtyInStock(qtyInStock);
		   article.setLastOutOfStock(new Date());

		   article.setTotalStockPrice(qtyInStock.multiply(article.getSppu()));

		   article.setRecordingDate(new Date());

		   update(article);
		}
  }
	 */
}
