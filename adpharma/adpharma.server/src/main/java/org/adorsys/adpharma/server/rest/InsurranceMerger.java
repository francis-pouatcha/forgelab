package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.repo.InsurranceRepository;

public class InsurranceMerger
{

   @Inject
   private InsurranceRepository repository;

   public Insurrance bindComposed(Insurrance entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Insurrance bindAggregated(Insurrance entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Insurrance> entities)
   {
      if (entities == null)
         return;
      HashSet<Insurrance> oldCol = new HashSet<Insurrance>(entities);
      entities.clear();
      for (Insurrance entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Insurrance> entities)
   {
      if (entities == null)
         return;
      HashSet<Insurrance> oldCol = new HashSet<Insurrance>(entities);
      entities.clear();
      for (Insurrance entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Insurrance unbind(final Insurrance entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Insurrance newEntity = new Insurrance();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return entity;
   }

   public Set<Insurrance> unbind(final Set<Insurrance> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Insurrance>();
      //       HashSet<Insurrance> cache = new HashSet<Insurrance>(entities);
      //       entities.clear();
      //       for (Insurrance entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
