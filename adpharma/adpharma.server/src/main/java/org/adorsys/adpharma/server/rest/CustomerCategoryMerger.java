package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.CustomerCategory;
import org.adorsys.adpharma.server.repo.CustomerCategoryRepository;

public class CustomerCategoryMerger
{

   @Inject
   private CustomerCategoryRepository repository;

   public CustomerCategory bindComposed(CustomerCategory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CustomerCategory bindAggregated(CustomerCategory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CustomerCategory> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerCategory> oldCol = new HashSet<CustomerCategory>(entities);
      entities.clear();
      for (CustomerCategory entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CustomerCategory> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerCategory> oldCol = new HashSet<CustomerCategory>(entities);
      entities.clear();
      for (CustomerCategory entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CustomerCategory unbind(final CustomerCategory entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CustomerCategory newEntity = new CustomerCategory();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CustomerCategory> unbind(final Set<CustomerCategory> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CustomerCategory>();
      //       HashSet<CustomerCategory> cache = new HashSet<CustomerCategory>(entities);
      //       entities.clear();
      //       for (CustomerCategory entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
