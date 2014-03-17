package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.ClearanceConfig;
import org.adorsys.adpharma.server.repo.ClearanceConfigRepository;

public class ClearanceConfigMerger
{

   @Inject
   private ClearanceConfigRepository repository;

   public ClearanceConfig bindComposed(ClearanceConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ClearanceConfig bindAggregated(ClearanceConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ClearanceConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<ClearanceConfig> oldCol = new HashSet<ClearanceConfig>(entities);
      entities.clear();
      for (ClearanceConfig entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ClearanceConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<ClearanceConfig> oldCol = new HashSet<ClearanceConfig>(entities);
      entities.clear();
      for (ClearanceConfig entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ClearanceConfig unbind(final ClearanceConfig entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ClearanceConfig newEntity = new ClearanceConfig();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ClearanceConfig> unbind(final Set<ClearanceConfig> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ClearanceConfig>();
      //       HashSet<ClearanceConfig> cache = new HashSet<ClearanceConfig>(entities);
      //       entities.clear();
      //       for (ClearanceConfig entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
