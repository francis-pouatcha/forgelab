package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentCreatedEvent;
import org.adorsys.adpharma.server.events.DocumentDeletedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem_;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.SalesOrderItemRepository;
import org.adorsys.adpharma.server.utils.CurencyUtil;
import org.adorsys.adpharma.server.utils.PeriodicalDataSearchInput;

@Stateless
public class SalesOrderItemEJB
{

	@Inject
	private SalesOrderItemRepository repository;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private SalesOrderMerger salesOrderMerger;

	@Inject
	@DocumentProcessedEvent
	private Event<SalesOrderItem> salesOrderItemProcessedEvent;

	@Inject
	@DocumentDeletedEvent
	private Event<SalesOrderItem> salesOrderItemDeletedEvent;

	@Inject
	@DocumentCreatedEvent
	private Event<SalesOrderItem> salesOrderItemCreatedEvent;

	public SalesOrderItem create(SalesOrderItem entity)
	{
		entity = attach(entity);
		SalesOrderItem orderItem = new SalesOrderItem();
		orderItem.setSalesOrder(entity.getSalesOrder());
		orderItem.setInternalPic(entity.getInternalPic());
		orderItem.setArticle(entity.getArticle());
		VAT vat = entity.getArticle().getVat();
		if(vat!=null)
			orderItem.setVat(vat);

		List<SalesOrderItem> found = findBy(orderItem, 0, 1, new SingularAttribute[]{SalesOrderItem_.internalPic,SalesOrderItem_.article,SalesOrderItem_.salesOrder});
		if(!found.isEmpty()){
			SalesOrderItem existingItem = found.iterator().next();
			existingItem.setOrderedQty(existingItem.getOrderedQty().add(entity.getOrderedQty()));
			existingItem.setSalesPricePU(entity.getSalesPricePU());
			existingItem.setPurchasePricePU(entity.getPurchasePricePU());
			existingItem.calucateDeliveryQty();
			existingItem.calculateAmount();
			existingItem = repository.save(existingItem);
			salesOrderItemCreatedEvent.fire(existingItem);
			return repository.save(existingItem);
		}
		entity.calucateDeliveryQty();
		entity.calculateAmount();
		entity = repository.save(entity);
		salesOrderItemCreatedEvent.fire(entity);
		return entity;
	}

	@Inject
	private EntityManager em ;

	public List<SalesOrderItem> periodicalSales(PeriodicalDataSearchInput searchInput){
		Boolean check = searchInput.getCheck();
		ArrayList<SalesOrderItem> result = new ArrayList<SalesOrderItem>();

		String query ="SELECT s.internalPic , s.article, s.deliveredQty,(s.deliveredQty * s.salesPricePU) FROM SalesOrderItem AS s WHERE  s.salesOrder.creationDate BETWEEN :from AND :to AND s.salesOrder.cashed = :cashed ORDER BY s.article.articleName ";
		List<Object[]> sales = new ArrayList<Object[]>();
		if(check)
			query ="SELECT s.internalPic , s.article, SUM(s.deliveredQty) AS qty ,SUM(s.deliveredQty * s.salesPricePU) FROM SalesOrderItem AS s WHERE "
					+ " s.salesOrder.creationDate BETWEEN :from AND :to AND s.salesOrder.cashed = :cashed  GROUP BY s.article , s.internalPic ";
		Query querys = em.createQuery(query) ;

		querys.setParameter("from", searchInput.getBeginDate());
		querys.setParameter("to", searchInput.getEndDate());
		querys.setParameter("cashed", Boolean.TRUE);
		sales = querys.getResultList();
		for (Object[] objects : sales) {
			SalesOrderItem item = new SalesOrderItem();
			String internalPic = (String) objects[0];
			Article article = (Article) objects[1];
			VAT vat = article.getVat();
			BigDecimal vatAmount = BigDecimal.ZERO ;	
			BigDecimal qty = (BigDecimal) objects[2];
			BigDecimal price = (BigDecimal) objects[3];
			if(vat!=null )
				vatAmount =	vat.getRate().multiply(price).divide(BigDecimal.valueOf(100));
			item.setVatValue(vatAmount);
			// remove non taxable sales
			if(searchInput.getTaxableSalesOnly()){
				if(BigDecimal.ZERO.compareTo(item.getVatValue())==0)
					continue ;
			}
			// remove taxable sales 
			if(searchInput.getNonTaxableSalesOnly()){
				if(BigDecimal.ZERO.compareTo(item.getVatValue())!=0)
					continue ;
			}

			//			if(searchInput.getTaxableSalesOnly()){
			//				if(vat!=null && BigDecimal.ZERO.compareTo(vat.getRate())!=0){
			//					vat.getRate().multiply(price).divide(BigDecimal.valueOf(1000));
			////					BigDecimal amountBeforeTax = CurencyUtil.round(totalSalePrice.divide(BigDecimal.ONE.add(vatRate), 2, RoundingMode.HALF_EVEN));
			//					
			//				}
			//			}

			item.setInternalPic(internalPic);
			item.setArticle(article);
			item.setDeliveredQty(qty);
			item.setTotalSalePrice(price);
			result.add(item);
		}
		return result ;
	}
	public SalesOrderItem deleteById(Long id)
	{
		SalesOrderItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
			salesOrderItemDeletedEvent.fire(entity);
		}
		return entity;
	}

	//	public SalesOrderItem update(SalesOrderItem entity)
	//	{
	//		entity.updateTotalSalesPrice();
	//		entity = repository.save(attach(entity));
	//		salesOrderItemProcessedEvent.fire(entity);
	//		return entity;
	//	}

	public SalesOrderItem update(SalesOrderItem entity)
	{
		SalesOrderItem attach = attach(entity);
		attach.calucateDeliveryQty();
		attach.calculateAmount();
		entity = repository.save(attach);
		salesOrderItemProcessedEvent.fire(entity);
		return entity;
	}
	public SalesOrderItem findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<SalesOrderItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<SalesOrderItem> findBy(SalesOrderItem entity, int start, int max, SingularAttribute<SalesOrderItem, ?>[] attributes)
	{
		SalesOrderItem salesOrderItem = attach(entity);
		return repository.findBy(salesOrderItem, start, max, attributes);
	}

	public Long countBy(SalesOrderItem entity, SingularAttribute<SalesOrderItem, ?>[] attributes)
	{
		SalesOrderItem salesOrderItem = attach(entity);
		return repository.count(salesOrderItem, attributes);
	}

	public List<SalesOrderItem> findByLike(SalesOrderItem entity, int start, int max, SingularAttribute<SalesOrderItem, ?>[] attributes)
	{
		SalesOrderItem salesOrderItem = attach(entity);
		return repository.findByLike(salesOrderItem, start, max, attributes);
	}

	public Long countByLike(SalesOrderItem entity, SingularAttribute<SalesOrderItem, ?>[] attributes)
	{
		SalesOrderItem salesOrderItem = attach(entity);
		return repository.countLike(salesOrderItem, attributes);
	}

	private SalesOrderItem attach(SalesOrderItem entity)
	{
		if (entity == null)
			return null;

		// composed

		entity.setSalesOrder(salesOrderMerger.bindAggregated(entity.getSalesOrder()));
		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		// aggregated
		entity.setVat(vATMerger.bindAggregated(entity.getVat()));

		return entity;
	}
}
