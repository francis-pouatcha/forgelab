package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc;
import org.adorsys.adpharma.server.repo.RoleNamePermissionNameAssocRepository;

public class RoleNamePermissionNameAssocMerger
{

   @Inject
   private RoleNamePermissionNameAssocRepository repository;

   public RoleNamePermissionNameAssoc bindComposed(RoleNamePermissionNameAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public RoleNamePermissionNameAssoc bindAggregated(RoleNamePermissionNameAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<RoleNamePermissionNameAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleNamePermissionNameAssoc> oldCol = new HashSet<RoleNamePermissionNameAssoc>(entities);
      entities.clear();
      for (RoleNamePermissionNameAssoc entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<RoleNamePermissionNameAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleNamePermissionNameAssoc> oldCol = new HashSet<RoleNamePermissionNameAssoc>(entities);
      entities.clear();
      for (RoleNamePermissionNameAssoc entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public RoleNamePermissionNameAssoc unbind(final RoleNamePermissionNameAssoc entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      RoleNamePermissionNameAssoc newEntity = new RoleNamePermissionNameAssoc();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<RoleNamePermissionNameAssoc> unbind(final Set<RoleNamePermissionNameAssoc> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<RoleNamePermissionNameAssoc>();
      //       HashSet<RoleNamePermissionNameAssoc> cache = new HashSet<RoleNamePermissionNameAssoc>(entities);
      //       entities.clear();
      //       for (RoleNamePermissionNameAssoc entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
