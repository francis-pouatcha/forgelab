package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.PermissionName;
import org.adorsys.adpharma.server.repo.PermissionNameRepository;

public class PermissionNameMerger
{

   @Inject
   private PermissionNameRepository repository;

   public PermissionName bindComposed(PermissionName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PermissionName bindAggregated(PermissionName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PermissionName> entities)
   {
      if (entities == null)
         return;
      HashSet<PermissionName> oldCol = new HashSet<PermissionName>(entities);
      entities.clear();
      for (PermissionName entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PermissionName> entities)
   {
      if (entities == null)
         return;
      HashSet<PermissionName> oldCol = new HashSet<PermissionName>(entities);
      entities.clear();
      for (PermissionName entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PermissionName unbind(final PermissionName entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PermissionName newEntity = new PermissionName();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PermissionName> unbind(final Set<PermissionName> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PermissionName>();
      //       HashSet<PermissionName> cache = new HashSet<PermissionName>(entities);
      //       entities.clear();
      //       for (PermissionName entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
