package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.repo.StockMovementRepository;

public class StockMovementMerger
{

   @Inject
   private StockMovementRepository repository;

   public StockMovement bindComposed(StockMovement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StockMovement bindAggregated(StockMovement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StockMovement> entities)
   {
      if (entities == null)
         return;
      HashSet<StockMovement> oldCol = new HashSet<StockMovement>(entities);
      entities.clear();
      for (StockMovement entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StockMovement> entities)
   {
      if (entities == null)
         return;
      HashSet<StockMovement> oldCol = new HashSet<StockMovement>(entities);
      entities.clear();
      for (StockMovement entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StockMovement unbind(final StockMovement entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StockMovement newEntity = new StockMovement();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StockMovement> unbind(final Set<StockMovement> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StockMovement>();
      //       HashSet<StockMovement> cache = new HashSet<StockMovement>(entities);
      //       entities.clear();
      //       for (StockMovement entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
