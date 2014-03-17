package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Hospital;
import org.adorsys.adpharma.server.repo.HospitalRepository;

public class HospitalMerger
{

   @Inject
   private HospitalRepository repository;

   public Hospital bindComposed(Hospital entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Hospital bindAggregated(Hospital entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Hospital> entities)
   {
      if (entities == null)
         return;
      HashSet<Hospital> oldCol = new HashSet<Hospital>(entities);
      entities.clear();
      for (Hospital entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Hospital> entities)
   {
      if (entities == null)
         return;
      HashSet<Hospital> oldCol = new HashSet<Hospital>(entities);
      entities.clear();
      for (Hospital entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Hospital unbind(final Hospital entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Hospital newEntity = new Hospital();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Hospital> unbind(final Set<Hospital> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Hospital>();
      //       HashSet<Hospital> cache = new HashSet<Hospital>(entities);
      //       entities.clear();
      //       for (Hospital entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
