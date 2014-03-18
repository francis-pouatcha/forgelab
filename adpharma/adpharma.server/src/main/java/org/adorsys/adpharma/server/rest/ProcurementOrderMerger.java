package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.repo.ProcurementOrderRepository;

public class ProcurementOrderMerger
{

   @Inject
   private ProcurementOrderRepository repository;

   public ProcurementOrder bindComposed(ProcurementOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ProcurementOrder bindAggregated(ProcurementOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ProcurementOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<ProcurementOrder> oldCol = new HashSet<ProcurementOrder>(entities);
      entities.clear();
      for (ProcurementOrder entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ProcurementOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<ProcurementOrder> oldCol = new HashSet<ProcurementOrder>(entities);
      entities.clear();
      for (ProcurementOrder entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ProcurementOrder unbind(final ProcurementOrder entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ProcurementOrder newEntity = new ProcurementOrder();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ProcurementOrder> unbind(final Set<ProcurementOrder> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ProcurementOrder>();
      //       HashSet<ProcurementOrder> cache = new HashSet<ProcurementOrder>(entities);
      //       entities.clear();
      //       for (ProcurementOrder entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
