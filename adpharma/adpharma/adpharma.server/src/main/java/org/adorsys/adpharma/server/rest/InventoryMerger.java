package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Inventory;
import org.adorsys.adpharma.server.repo.InventoryRepository;

public class InventoryMerger
{

   @Inject
   private InventoryRepository repository;

   public Inventory bindComposed(Inventory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Inventory bindAggregated(Inventory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Inventory> entities)
   {
      if (entities == null)
         return;
      HashSet<Inventory> oldCol = new HashSet<Inventory>(entities);
      entities.clear();
      for (Inventory entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Inventory> entities)
   {
      if (entities == null)
         return;
      HashSet<Inventory> oldCol = new HashSet<Inventory>(entities);
      entities.clear();
      for (Inventory entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Inventory unbind(final Inventory entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Inventory newEntity = new Inventory();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Inventory> unbind(final Set<Inventory> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Inventory>();
      //       HashSet<Inventory> cache = new HashSet<Inventory>(entities);
      //       entities.clear();
      //       for (Inventory entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
