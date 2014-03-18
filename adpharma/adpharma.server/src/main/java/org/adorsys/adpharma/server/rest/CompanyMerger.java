package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Company;
import org.adorsys.adpharma.server.repo.CompanyRepository;

public class CompanyMerger
{

   @Inject
   private CompanyRepository repository;

   public Company bindComposed(Company entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Company bindAggregated(Company entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Company> entities)
   {
      if (entities == null)
         return;
      HashSet<Company> oldCol = new HashSet<Company>(entities);
      entities.clear();
      for (Company entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Company> entities)
   {
      if (entities == null)
         return;
      HashSet<Company> oldCol = new HashSet<Company>(entities);
      entities.clear();
      for (Company entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Company unbind(final Company entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Company newEntity = new Company();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Company> unbind(final Set<Company> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Company>();
      //       HashSet<Company> cache = new HashSet<Company>(entities);
      //       entities.clear();
      //       for (Company entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
