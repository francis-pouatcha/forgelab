package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.repo.PaymentRepository;

public class PaymentMerger
{

   @Inject
   private PaymentRepository repository;

   public Payment bindComposed(Payment entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Payment bindAggregated(Payment entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Payment> entities)
   {
      if (entities == null)
         return;
      HashSet<Payment> oldCol = new HashSet<Payment>(entities);
      entities.clear();
      for (Payment entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Payment> entities)
   {
      if (entities == null)
         return;
      HashSet<Payment> oldCol = new HashSet<Payment>(entities);
      entities.clear();
      for (Payment entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Payment unbind(final Payment entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Payment newEntity = new Payment();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Payment> unbind(final Set<Payment> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Payment>();
      //       HashSet<Payment> cache = new HashSet<Payment>(entities);
      //       entities.clear();
      //       for (Payment entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
