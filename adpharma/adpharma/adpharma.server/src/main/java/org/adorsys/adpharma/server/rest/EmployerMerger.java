package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Employer;
import org.adorsys.adpharma.server.repo.EmployerRepository;

public class EmployerMerger
{

   @Inject
   private EmployerRepository repository;

   public Employer bindComposed(Employer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Employer bindAggregated(Employer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Employer> entities)
   {
      if (entities == null)
         return;
      HashSet<Employer> oldCol = new HashSet<Employer>(entities);
      entities.clear();
      for (Employer entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Employer> entities)
   {
      if (entities == null)
         return;
      HashSet<Employer> oldCol = new HashSet<Employer>(entities);
      entities.clear();
      for (Employer entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Employer unbind(final Employer entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Employer newEntity = new Employer();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Employer> unbind(final Set<Employer> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Employer>();
      //       HashSet<Employer> cache = new HashSet<Employer>(entities);
      //       entities.clear();
      //       for (Employer entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
