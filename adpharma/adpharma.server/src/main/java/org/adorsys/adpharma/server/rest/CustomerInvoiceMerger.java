package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;

public class CustomerInvoiceMerger
{

   @Inject
   private CustomerInvoiceRepository repository;

   public CustomerInvoice bindComposed(CustomerInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CustomerInvoice bindAggregated(CustomerInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CustomerInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoice> oldCol = new HashSet<CustomerInvoice>(entities);
      entities.clear();
      for (CustomerInvoice entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CustomerInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoice> oldCol = new HashSet<CustomerInvoice>(entities);
      entities.clear();
      for (CustomerInvoice entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CustomerInvoice unbind(final CustomerInvoice entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CustomerInvoice newEntity = new CustomerInvoice();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      newEntity.setInsurance(entity.getInsurance());
      newEntity.setCustomer(entity.getCustomer());
      SalesOrder salesOrder = new SalesOrder();
      if(entity.getSalesOrder()!=null){
      salesOrder.setId(entity.getSalesOrder().getId());
      salesOrder.setSoNumber(entity.getSalesOrder().getSoNumber());
      newEntity.setSalesOrder(salesOrder);
      }
      return newEntity;
   }

   public Set<CustomerInvoice> unbind(final Set<CustomerInvoice> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CustomerInvoice>();
      //       HashSet<CustomerInvoice> cache = new HashSet<CustomerInvoice>(entities);
      //       entities.clear();
      //       for (CustomerInvoice entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
