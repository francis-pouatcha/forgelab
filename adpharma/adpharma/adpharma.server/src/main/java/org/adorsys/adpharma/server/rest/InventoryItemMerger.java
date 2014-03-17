package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.InventoryItem;
import org.adorsys.adpharma.server.repo.InventoryItemRepository;

public class InventoryItemMerger
{

   @Inject
   private InventoryItemRepository repository;

   public InventoryItem bindComposed(InventoryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public InventoryItem bindAggregated(InventoryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<InventoryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<InventoryItem> oldCol = new HashSet<InventoryItem>(entities);
      entities.clear();
      for (InventoryItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<InventoryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<InventoryItem> oldCol = new HashSet<InventoryItem>(entities);
      entities.clear();
      for (InventoryItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public InventoryItem unbind(final InventoryItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      InventoryItem newEntity = new InventoryItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<InventoryItem> unbind(final Set<InventoryItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<InventoryItem>();
      //       HashSet<InventoryItem> cache = new HashSet<InventoryItem>(entities);
      //       entities.clear();
      //       for (InventoryItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
