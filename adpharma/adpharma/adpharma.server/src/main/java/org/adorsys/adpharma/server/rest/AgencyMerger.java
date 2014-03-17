package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.repo.AgencyRepository;

public class AgencyMerger
{

   @Inject
   private AgencyRepository repository;

   public Agency bindComposed(Agency entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Agency bindAggregated(Agency entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Agency> entities)
   {
      if (entities == null)
         return;
      HashSet<Agency> oldCol = new HashSet<Agency>(entities);
      entities.clear();
      for (Agency entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Agency> entities)
   {
      if (entities == null)
         return;
      HashSet<Agency> oldCol = new HashSet<Agency>(entities);
      entities.clear();
      for (Agency entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Agency unbind(final Agency entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Agency newEntity = new Agency();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Agency> unbind(final Set<Agency> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Agency>();
      //       HashSet<Agency> cache = new HashSet<Agency>(entities);
      //       entities.clear();
      //       for (Agency entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
