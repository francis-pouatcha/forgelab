package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.SalesOrderItemRepository;

public class SalesOrderItemMerger
{

   @Inject
   private SalesOrderItemRepository repository;

   public SalesOrderItem bindComposed(SalesOrderItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SalesOrderItem bindAggregated(SalesOrderItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SalesOrderItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrderItem> oldCol = new HashSet<SalesOrderItem>(entities);
      entities.clear();
      for (SalesOrderItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SalesOrderItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrderItem> oldCol = new HashSet<SalesOrderItem>(entities);
      entities.clear();
      for (SalesOrderItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SalesOrderItem unbind(final SalesOrderItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SalesOrderItem newEntity = new SalesOrderItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SalesOrderItem> unbind(final Set<SalesOrderItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SalesOrderItem>();
      //       HashSet<SalesOrderItem> cache = new HashSet<SalesOrderItem>(entities);
      //       entities.clear();
      //       for (SalesOrderItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
