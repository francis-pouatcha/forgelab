package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;

public class SalesOrderMerger
{

   @Inject
   private SalesOrderRepository repository;

   public SalesOrder bindComposed(SalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SalesOrder bindAggregated(SalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrder> oldCol = new HashSet<SalesOrder>(entities);
      entities.clear();
      for (SalesOrder entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrder> oldCol = new HashSet<SalesOrder>(entities);
      entities.clear();
      for (SalesOrder entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SalesOrder unbind(final SalesOrder entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SalesOrder newEntity = new SalesOrder();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SalesOrder> unbind(final Set<SalesOrder> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SalesOrder>();
      //       HashSet<SalesOrder> cache = new HashSet<SalesOrder>(entities);
      //       entities.clear();
      //       for (SalesOrder entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
