package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.repo.ProcurementOrderItemRepository;

public class ProcurementOrderItemMerger
{

   @Inject
   private ProcurementOrderItemRepository repository;

   public ProcurementOrderItem bindComposed(ProcurementOrderItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ProcurementOrderItem bindAggregated(ProcurementOrderItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ProcurementOrderItem> entities)
   {
      if (entities == null)
         return;
      HashSet<ProcurementOrderItem> oldCol = new HashSet<ProcurementOrderItem>(entities);
      entities.clear();
      for (ProcurementOrderItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ProcurementOrderItem> entities)
   {
      if (entities == null)
         return;
      HashSet<ProcurementOrderItem> oldCol = new HashSet<ProcurementOrderItem>(entities);
      entities.clear();
      for (ProcurementOrderItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ProcurementOrderItem unbind(final ProcurementOrderItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ProcurementOrderItem newEntity = new ProcurementOrderItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ProcurementOrderItem> unbind(final Set<ProcurementOrderItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ProcurementOrderItem>();
      //       HashSet<ProcurementOrderItem> cache = new HashSet<ProcurementOrderItem>(entities);
      //       entities.clear();
      //       for (ProcurementOrderItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
