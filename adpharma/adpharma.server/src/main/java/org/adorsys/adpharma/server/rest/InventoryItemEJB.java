package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.InventoryItem;
import org.adorsys.adpharma.server.repo.InventoryItemRepository;

@Stateless
public class InventoryItemEJB
{

   @Inject
   private InventoryItemRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private InventoryMerger inventoryMerger;

   @Inject
   private ArticleMerger articleMerger;

   public InventoryItem create(InventoryItem entity)
   {
      return repository.save(attach(entity));
   }

   public InventoryItem deleteById(Long id)
   {
      InventoryItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InventoryItem update(InventoryItem entity)
   {
      return repository.save(attach(entity));
   }

   public InventoryItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<InventoryItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InventoryItem> findBy(InventoryItem entity, int start, int max, SingularAttribute<InventoryItem, ?>[] attributes)
   {
	   InventoryItem inventoryItem = attach(entity);
      return repository.findBy(inventoryItem, start, max, attributes);
   }

   public Long countBy(InventoryItem entity, SingularAttribute<InventoryItem, ?>[] attributes)
   {
	   InventoryItem inventoryItem = attach(entity);
      return repository.count(inventoryItem, attributes);
   }

   public List<InventoryItem> findByLike(InventoryItem entity, int start, int max, SingularAttribute<InventoryItem, ?>[] attributes)
   {
	   InventoryItem inventoryItem = attach(entity);
      return repository.findByLike(inventoryItem, start, max, attributes);
   }

   public Long countByLike(InventoryItem entity, SingularAttribute<InventoryItem, ?>[] attributes)
   {
	   InventoryItem inventoryItem = attach(entity);
      return repository.countLike(inventoryItem, attributes);
   }

   private InventoryItem attach(InventoryItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      return entity;
   }
}
