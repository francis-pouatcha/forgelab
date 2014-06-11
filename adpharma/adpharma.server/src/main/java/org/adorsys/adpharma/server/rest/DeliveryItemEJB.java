package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.EntityEditDoneRequestEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItem_;
import org.adorsys.adpharma.server.jpa.Login;
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
