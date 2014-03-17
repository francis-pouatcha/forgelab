package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.repo.PaymentCustomerInvoiceAssocRepository;

public class PaymentCustomerInvoiceAssocMerger
{

   @Inject
   private PaymentCustomerInvoiceAssocRepository repository;

   public PaymentCustomerInvoiceAssoc bindComposed(PaymentCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PaymentCustomerInvoiceAssoc bindAggregated(PaymentCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PaymentCustomerInvoiceAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<PaymentCustomerInvoiceAssoc> oldCol = new HashSet<PaymentCustomerInvoiceAssoc>(entities);
      entities.clear();
      for (PaymentCustomerInvoiceAssoc entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PaymentCustomerInvoiceAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<PaymentCustomerInvoiceAssoc> oldCol = new HashSet<PaymentCustomerInvoiceAssoc>(entities);
      entities.clear();
      for (PaymentCustomerInvoiceAssoc entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PaymentCustomerInvoiceAssoc unbind(final PaymentCustomerInvoiceAssoc entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PaymentCustomerInvoiceAssoc newEntity = new PaymentCustomerInvoiceAssoc();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PaymentCustomerInvoiceAssoc> unbind(final Set<PaymentCustomerInvoiceAssoc> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PaymentCustomerInvoiceAssoc>();
      //       HashSet<PaymentCustomerInvoiceAssoc> cache = new HashSet<PaymentCustomerInvoiceAssoc>(entities);
      //       entities.clear();
      //       for (PaymentCustomerInvoiceAssoc entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
