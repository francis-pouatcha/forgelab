package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.PackagingMode;
import org.adorsys.adpharma.server.repo.PackagingModeRepository;

public class PackagingModeMerger
{

   @Inject
   private PackagingModeRepository repository;

   public PackagingMode bindComposed(PackagingMode entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PackagingMode bindAggregated(PackagingMode entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PackagingMode> entities)
   {
      if (entities == null)
         return;
      HashSet<PackagingMode> oldCol = new HashSet<PackagingMode>(entities);
      entities.clear();
      for (PackagingMode entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PackagingMode> entities)
   {
      if (entities == null)
         return;
      HashSet<PackagingMode> oldCol = new HashSet<PackagingMode>(entities);
      entities.clear();
      for (PackagingMode entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PackagingMode unbind(final PackagingMode entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PackagingMode newEntity = new PackagingMode();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PackagingMode> unbind(final Set<PackagingMode> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PackagingMode>();
      //       HashSet<PackagingMode> cache = new HashSet<PackagingMode>(entities);
      //       entities.clear();
      //       for (PackagingMode entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
