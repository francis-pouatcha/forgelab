package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.CustomerVoucher;
import org.adorsys.adpharma.server.repo.CustomerVoucherRepository;

public class CustomerVoucherMerger
{

   @Inject
   private CustomerVoucherRepository repository;

   public CustomerVoucher bindComposed(CustomerVoucher entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CustomerVoucher bindAggregated(CustomerVoucher entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CustomerVoucher> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerVoucher> oldCol = new HashSet<CustomerVoucher>(entities);
      entities.clear();
      for (CustomerVoucher entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CustomerVoucher> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerVoucher> oldCol = new HashSet<CustomerVoucher>(entities);
      entities.clear();
      for (CustomerVoucher entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CustomerVoucher unbind(final CustomerVoucher entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CustomerVoucher newEntity = new CustomerVoucher();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CustomerVoucher> unbind(final Set<CustomerVoucher> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CustomerVoucher>();
      //       HashSet<CustomerVoucher> cache = new HashSet<CustomerVoucher>(entities);
      //       entities.clear();
      //       for (CustomerVoucher entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
