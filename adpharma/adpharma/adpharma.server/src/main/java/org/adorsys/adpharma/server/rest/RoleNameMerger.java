package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.RoleName;
import org.adorsys.adpharma.server.repo.RoleNameRepository;

public class RoleNameMerger
{

   @Inject
   private RoleNameRepository repository;

   public RoleName bindComposed(RoleName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public RoleName bindAggregated(RoleName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<RoleName> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleName> oldCol = new HashSet<RoleName>(entities);
      entities.clear();
      for (RoleName entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<RoleName> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleName> oldCol = new HashSet<RoleName>(entities);
      entities.clear();
      for (RoleName entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public RoleName unbind(final RoleName entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      RoleName newEntity = new RoleName();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<RoleName> unbind(final Set<RoleName> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<RoleName>();
      //       HashSet<RoleName> cache = new HashSet<RoleName>(entities);
      //       entities.clear();
      //       for (RoleName entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
