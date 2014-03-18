package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.repo.DeliveryItemRepository;

public class DeliveryItemMerger
{

   @Inject
   private DeliveryItemRepository repository;

   public DeliveryItem bindComposed(DeliveryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public DeliveryItem bindAggregated(DeliveryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<DeliveryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<DeliveryItem> oldCol = new HashSet<DeliveryItem>(entities);
      entities.clear();
      for (DeliveryItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<DeliveryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<DeliveryItem> oldCol = new HashSet<DeliveryItem>(entities);
      entities.clear();
      for (DeliveryItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public DeliveryItem unbind(final DeliveryItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      DeliveryItem newEntity = new DeliveryItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<DeliveryItem> unbind(final Set<DeliveryItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<DeliveryItem>();
      //       HashSet<DeliveryItem> cache = new HashSet<DeliveryItem>(entities);
      //       entities.clear();
      //       for (DeliveryItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
