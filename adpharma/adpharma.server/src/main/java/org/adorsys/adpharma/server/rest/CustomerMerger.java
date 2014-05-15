package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.repo.CustomerRepository;

public class CustomerMerger
{

   @Inject
   private CustomerRepository repository;

   public Customer bindComposed(Customer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Customer bindAggregated(Customer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Customer> entities)
   {
      if (entities == null)
         return;
      HashSet<Customer> oldCol = new HashSet<Customer>(entities);
      entities.clear();
      for (Customer entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Customer> entities)
   {
      if (entities == null)
         return;
      HashSet<Customer> oldCol = new HashSet<Customer>(entities);
      entities.clear();
      for (Customer entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Customer unbind(final Customer entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Customer newEntity = new Customer();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      newEntity.setCustomerCategory(entity.getCustomerCategory());
      return newEntity;
   }

   public Set<Customer> unbind(final Set<Customer> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Customer>();
      //       HashSet<Customer> cache = new HashSet<Customer>(entities);
      //       entities.clear();
      //       for (Customer entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
