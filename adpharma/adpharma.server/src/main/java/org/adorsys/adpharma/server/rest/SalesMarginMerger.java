package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.SalesMargin;
import org.adorsys.adpharma.server.repo.SalesMarginRepository;

public class SalesMarginMerger
{

   @Inject
   private SalesMarginRepository repository;

   public SalesMargin bindComposed(SalesMargin entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SalesMargin bindAggregated(SalesMargin entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SalesMargin> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesMargin> oldCol = new HashSet<SalesMargin>(entities);
      entities.clear();
      for (SalesMargin entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SalesMargin> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesMargin> oldCol = new HashSet<SalesMargin>(entities);
      entities.clear();
      for (SalesMargin entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SalesMargin unbind(final SalesMargin entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SalesMargin newEntity = new SalesMargin();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SalesMargin> unbind(final Set<SalesMargin> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SalesMargin>();
      //       HashSet<SalesMargin> cache = new HashSet<SalesMargin>(entities);
      //       entities.clear();
      //       for (SalesMargin entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
