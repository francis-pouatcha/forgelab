package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.repo.PaymentItemRepository;

public class PaymentItemMerger
{

   @Inject
   private PaymentItemRepository repository;

   public PaymentItem bindComposed(PaymentItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PaymentItem bindAggregated(PaymentItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PaymentItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PaymentItem> oldCol = new HashSet<PaymentItem>(entities);
      entities.clear();
      for (PaymentItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PaymentItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PaymentItem> oldCol = new HashSet<PaymentItem>(entities);
      entities.clear();
      for (PaymentItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PaymentItem unbind(final PaymentItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PaymentItem newEntity = new PaymentItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PaymentItem> unbind(final Set<PaymentItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PaymentItem>();
      //       HashSet<PaymentItem> cache = new HashSet<PaymentItem>(entities);
      //       entities.clear();
      //       for (PaymentItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
