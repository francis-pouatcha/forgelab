package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentCanceledEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.ArticleLotRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;

@Stateless
public class ArticleLotEJB
{

	@Inject
	private ArticleLotRepository repository;

	@Inject
	private ApplicationConfiguration applicationConfiguration;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private ArticleMerger articleMerger;

	@EJB
	private SecurityUtil securityUtil;

	public ArticleLot create(ArticleLot entity)
	{
		return repository.save(attach(entity));
	}

	public ArticleLot deleteById(Long id)
	{
		ArticleLot entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public ArticleLot update(ArticleLot entity)
	{
		return repository.save(attach(entity));
	}

	public ArticleLot findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<ArticleLot> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<ArticleLot> findBy(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<ArticleLot> findByLike(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private ArticleLot attach(ArticleLot entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		return entity;
	}

	public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery){
		Login creatingUser = securityUtil.getConnectedUser();
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");
		// generate Article lot for each delivery item
		if(isManagedLot){
			for (DeliveryItem deliveryItem : deliveryItems) {
				ArticleLot al = new  ArticleLot();
				al =ArticleLot.fromDeliveryItem(deliveryItem, creatingUser);
				al = create(al);
			}
		}else {
			for (DeliveryItem deliveryItem : deliveryItems) {
				ArticleLot al = new  ArticleLot();
				al.setArticle(deliveryItem.getArticle());
				al.setAgency(deliveryItem.getDelivery().getReceivingAgency());
				List<ArticleLot> found = findByLike(al, 0, 1, new SingularAttribute[]{ArticleLot_.article,ArticleLot_.agency});
				if(!found.isEmpty()){
					al = found.iterator().next();
					if(deliveryItem.getArticle()!=null)
						al.setArticleName(deliveryItem.getArticle().getArticleName());
					al.setExpirationDate(deliveryItem.getExpirationDate());
					al.setPurchasePricePU(deliveryItem.getPurchasePricePU());
					al.setSalesPricePU(deliveryItem.getSalesPricePU());
					al.setStockQuantity(al.getStockQuantity().add(deliveryItem.getStockQuantity()));
					al.calculateTotalAmout();
					al = update(al);
				}else {
					al = ArticleLot.fromDeliveryItem(deliveryItem, creatingUser);
					al = create(al);
				}

			}
		}

	}

	/**
	 * Process a completed sales. Will process with the known lot if provided if not
	 * will use the FIFO technique to manage stocks. 
	 * 
	 * @param closedSales
	 */
	public void handleSalesClosed(@Observes @DocumentClosedEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			String internalPic = salesOrderItem.getInternalPic();
			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(internalPic);
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = findByLike(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			articleLot = found.iterator().next();

			BigDecimal currenQtyInStock = articleLot.getStockQuantity()==null?BigDecimal.ZERO:articleLot.getStockQuantity();
			BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

			BigDecimal qtyInStock = currenQtyInStock.subtract(releasingQty).add(returnedQty);
			articleLot.setStockQuantity(qtyInStock);
			articleLot.setTotalPurchasePrice(qtyInStock.multiply(articleLot.getPurchasePricePU()));
			articleLot.setTotalSalePrice(qtyInStock.multiply(articleLot.getSalesPricePU()));

			update(articleLot);
		}
	}

	/**
	 * Process a canceled sales. Will process with the known lot if provided if not
	 * will use the FIFO technique to manage stocks. 
	 * 
	 * @param canceledSales
	 */
	public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			String internalPic = salesOrderItem.getInternalPic();
			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(internalPic);
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = findByLike(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			articleLot = found.iterator().next();

			BigDecimal currenQtyInStock = articleLot.getStockQuantity()==null?BigDecimal.ZERO:articleLot.getStockQuantity();
			BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

			BigDecimal qtyInStock = currenQtyInStock.add(releasingQty).subtract(returnedQty);
			articleLot.setStockQuantity(qtyInStock);
			articleLot.setTotalPurchasePrice(qtyInStock.multiply(articleLot.getPurchasePricePU()));
			articleLot.setTotalSalePrice(qtyInStock.multiply(articleLot.getSalesPricePU()));

			update(articleLot);
		}
	}
}
