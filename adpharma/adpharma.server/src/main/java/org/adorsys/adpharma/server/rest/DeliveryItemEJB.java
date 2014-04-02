package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.repo.DeliveryItemRepository;

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

   public DeliveryItem create(DeliveryItem entity)
   {
      return repository.save(attach(entity));
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

   public DeliveryItem update(DeliveryItem entity)
   {
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
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DeliveryItem entity, SingularAttribute<DeliveryItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DeliveryItem> findByLike(DeliveryItem entity, int start, int max, SingularAttribute<DeliveryItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DeliveryItem entity, SingularAttribute<DeliveryItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DeliveryItem attach(DeliveryItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      return entity;
   }
}
