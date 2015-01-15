package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.EntityEditDoneRequestEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleDetails;
import org.adorsys.adpharma.server.jpa.ArticleSearchInput;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.PeriodicalDeliveryDataSearchInput;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem_;
import org.adorsys.adpharma.server.repo.DeliveryItemRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

@Stateless
public class DeliveryItemEJB
{

	@Inject
	private DeliveryItemRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private DeliveryMerger deliveryMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private SecurityUtil securityUtilEJB;

	@Inject
	@EntityEditDoneRequestEvent
	private Event<DeliveryItem> deliveryItemEditRequestEvent;

	public DeliveryItem create(DeliveryItem entity)
	{
		entity = attach(entity);
		Login login = securityUtilEJB.getConnectedUser();
		entity.setCreatingUser(login);
		entity.calculateAmount();

		List<DeliveryItem> found = findBy(entity, 0, 1, new SingularAttribute[]{DeliveryItem_.mainPic,DeliveryItem_.article,DeliveryItem_.delivery});
		if(!found.isEmpty()){
             DeliveryItem next = found.iterator().next();
             next.setQtyOrdered(next.getQtyOrdered().add(entity.getQtyOrdered()));
             next.setAvailableQty(next.getAvailableQty().add(entity.getAvailableQty()));
             next.setStockQuantity(entity.getStockQuantity().add(next.getStockQuantity()));
             next.setFreeQuantity(next.getFreeQuantity().add(entity.getFreeQuantity()));
             next.setPurchasePricePU(entity.getPurchasePricePU());
             next.setSalesPricePU(entity.getSalesPricePU());
             next.setExpirationDate(entity.getExpirationDate());
             next.calculateAmount();
             return repository.save(next);
		}
		return repository.save(entity);
	}

	public DeliveryItem deleteById(Long id)
	{
		DeliveryItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public void handleArticleChange(@Observes @EntityEditDoneRequestEvent Article article){

		DeliveryItem item = new DeliveryItem();
		item.setArticle(article);
		List<DeliveryItem> found = findBy(item, 0, -1, new SingularAttribute[]{DeliveryItem_.article});
		if(!found.isEmpty()){
			for (DeliveryItem lot : found) {
				lot.setArticleName(article.getArticleName());
				lot.setMainPic(article.getPic());
				repository.save(lot);
			}
		}
	}
	public DeliveryItem update(DeliveryItem entity)
	{
		//		entity.calculateAmount();
		DeliveryItem save = repository.save(attach(entity));
		deliveryItemEditRequestEvent.fire(save);
		return save;
	}

	public DeliveryItem findById(Long id)
	{
		return repository.findBy(id);
	}
// Francis 01/15/2015 this is not good
//	public List<DeliveryItem> findByDelivery(Delivery delivery){
//		return repository.findByDelivery(delivery);
//	}

	public List<DeliveryItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	@Inject
	private EntityManager em ;

	public List<DeliveryItem> periodicalDeliveryRepport(PeriodicalDeliveryDataSearchInput searchInput){
		Boolean goup = searchInput.getCheck();
		ArrayList<DeliveryItem> result = new ArrayList<DeliveryItem>();
		String query = " " ;
		List<Object[]> sales = new ArrayList<Object[]>();
		if(searchInput.getSupplier()!=null && searchInput.getSupplier().getId()!=null)
			query = query=" AND s.delivery.supplier = :supplier " ;
		if(goup){
			query = "SELECT s.internalPic , s.article, SUM(s.stockQuantity),SUM((s.stockQuantity - s.freeQuantity) * s.purchasePricePU) FROM DeliveryItem AS s WHERE "
					+ "  s.delivery.recordingDate BETWEEN :from AND :to AND s.delivery.deliveryProcessingState = :state"+query+" GROUP BY s.article ORDER BY SUM(s.stockQuantity) DESC" ;
		}else {
			query = "SELECT s.internalPic , s.article, s.stockQuantity,((s.stockQuantity - s.freeQuantity) * s.purchasePricePU) "
					+ "FROM DeliveryItem AS s WHERE  s.delivery.recordingDate BETWEEN :from AND :to AND s.delivery.deliveryProcessingState = :state"+
					query+" ORDER BY s.article.articleName " ;
		}
		Query querys = em.createQuery(query) ;

		querys.setParameter("from", searchInput.getBeginDate());
		querys.setParameter("to", searchInput.getEndDate());
		querys.setParameter("state",DocumentProcessingState.CLOSED);
		if(searchInput.getSupplier()!=null && searchInput.getSupplier().getId()!=null)
			querys.setParameter("supplier",searchInput.getSupplier());
		sales = querys.getResultList();
		for (Object[] objects : sales) {
			DeliveryItem item = new DeliveryItem();
			String internalPic = (String) objects[0];
			Article article = (Article) objects[1];
			BigDecimal qty = (BigDecimal) objects[2];
			BigDecimal price = (BigDecimal) objects[3];
			item.setInternalPic(internalPic);
			item.setArticle(article);
			item.setQtyOrdered(qty);
			item.setTotalPurchasePrice(price);
			result.add(item);
		}
		return result ;
	}
	
	public List<ArticleDetails> findArticlesDetails(ArticleSearchInput input){
		String articleName = input.getEntity().getArticleName();
		String pic = input.getEntity().getPic();
		int max = input.getMax();
		List<ArticleDetails> result= new ArrayList<ArticleDetails>();
		List<Object[]> articles= new ArrayList<Object[]>();
		StringBuilder query = new StringBuilder("SELECT de.internalPic, de.mainPic, de.articleName, de.delivery.supplier.name, de.delivery.deliveryDate, de.purchasePricePU FROM DeliveryItem AS de");
		query.append(" WHERE de.id IS NOT NULL AND de.articleName = :designation AND de.mainPic = :cip");
		query.append(" ORDER BY de.delivery.deliveryDate DESC");
		
		Query createQuery = em.createQuery(query.toString());
		createQuery.setParameter("designation", articleName);
		createQuery.setParameter("cip", pic);
		createQuery.setMaxResults(max);
		articles = createQuery.getResultList();
		for(Object[] objects: articles) {
			String internalPic= (String)objects[0];
			String mainPic= (String)objects[1];
			String article= (String)objects[2];
			String supplier= (String)objects[3];
			Date deliveryDate= (Date)objects[4];
			BigDecimal ppu= (BigDecimal)objects[5];
			ArticleDetails articleDetails = new ArticleDetails();
            articleDetails.setInternalPic(internalPic);
            articleDetails.setMainPic(mainPic);
            articleDetails.setArticleName(article);
            articleDetails.setSupplier(supplier);
            articleDetails.setDeliveryDate(deliveryDate);
            articleDetails.setPurchasePricePU(ppu);
			result.add(articleDetails);
		}
		return result;
	}

	public List<DeliveryItem> findBy(DeliveryItem entity, int start, int max, SingularAttribute<DeliveryItem, ?>[] attributes)
	{
		DeliveryItem deliveryItem = attach(entity);
		return repository.findBy(deliveryItem, start, max, attributes);
	}

	public Long countBy(DeliveryItem entity, SingularAttribute<DeliveryItem, ?>[] attributes)
	{
		DeliveryItem deliveryItem = attach(entity);
		return repository.count(deliveryItem, attributes);
	}

	public List<DeliveryItem> findByLike(DeliveryItem entity, int start, int max, SingularAttribute<DeliveryItem, ?>[] attributes)
	{
		DeliveryItem deliveryItem = attach(entity);
		return repository.findByLike(deliveryItem, start, max, attributes);
	}

	public Long countByLike(DeliveryItem entity, SingularAttribute<DeliveryItem, ?>[] attributes)
	{
		DeliveryItem deliveryItem = attach(entity);
		return repository.countLike(deliveryItem, attributes);
	}

	private DeliveryItem attach(DeliveryItem entity)
	{
		if (entity == null)
			return null;

		// composed
		entity.setDelivery(deliveryMerger.bindAggregated(entity.getDelivery()));
		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		return entity;
	}
}
