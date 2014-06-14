package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.EntityEditDoneRequestEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.PeriodicalDeliveryDataSearchInput;
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

	public DeliveryItem create(DeliveryItem entity)
	{
		entity = attach(entity);
		Login login = securityUtilEJB.getConnectedUser();
		entity.setCreatingUser(login);
		entity.calculateAmount();
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
				update(lot);
			}
		}
	}
	public DeliveryItem update(DeliveryItem entity)
	{
		entity = attach(entity);
		entity.calculateAmount();
		return repository.save(attach(entity));
	}

	public DeliveryItem findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<DeliveryItem> findByDelivery(Delivery delivery){
		return repository.findByDelivery(delivery);
	}

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
