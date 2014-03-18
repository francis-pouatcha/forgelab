package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Prescriber;
import org.adorsys.adpharma.server.repo.PrescriberRepository;

public class PrescriberMerger
{

   @Inject
   private PrescriberRepository repository;

   public Prescriber bindComposed(Prescriber entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Prescriber bindAggregated(Prescriber entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Prescriber> entities)
   {
      if (entities == null)
         return;
      HashSet<Prescriber> oldCol = new HashSet<Prescriber>(entities);
      entities.clear();
      for (Prescriber entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Prescriber> entities)
   {
      if (entities == null)
         return;
      HashSet<Prescriber> oldCol = new HashSet<Prescriber>(entities);
      entities.clear();
      for (Prescriber entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Prescriber unbind(final Prescriber entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Prescriber newEntity = new Prescriber();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Prescriber> unbind(final Set<Prescriber> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Prescriber>();
      //       HashSet<Prescriber> cache = new HashSet<Prescriber>(entities);
      //       entities.clear();
      //       for (Prescriber entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
