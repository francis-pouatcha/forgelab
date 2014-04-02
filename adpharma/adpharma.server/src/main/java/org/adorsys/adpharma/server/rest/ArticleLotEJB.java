package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedDoneEvent;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.ArticleLotRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

@Stateless
public class ArticleLotEJB
{

   @Inject
   private ArticleLotRepository repository;

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

	public void generateArticleLot(@Observes @DocumentClosedDoneEvent Delivery closedDelivery){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();

		// generate Article lot for each delivery item
		for (DeliveryItem deliveryItem : deliveryItems) {
			ArticleLot al = new  ArticleLot();
			al.setAgency(creatingUser.getAgency());
			al.setArticle(deliveryItem.getArticle());
			if(deliveryItem.getArticle()!=null)
				al.setArticleName(deliveryItem.getArticle().getArticleName());
			al.setCreationDate(creationDate);
			al.setExpirationDate(deliveryItem.getExpirationDate());
			al.setInternalPic(deliveryItem.getInternalPic());
			al.setMainPic(deliveryItem.getMainPic());
			al.setSecondaryPic(deliveryItem.getSecondaryPic());
			al.setPurchasePricePU(deliveryItem.getPurchasePricePU());
			al.setSalesPricePU(deliveryItem.getSalesPricePU());
			al.setStockQuantity(deliveryItem.getStockQuantity());
			al.setTotalPurchasePrice(deliveryItem.getTotalPurchasePrice());
			al.setTotalSalePrice(deliveryItem.getSalesPricePU().multiply(deliveryItem.getStockQuantity()));
			al = create(al);
		}
	}

   
}
