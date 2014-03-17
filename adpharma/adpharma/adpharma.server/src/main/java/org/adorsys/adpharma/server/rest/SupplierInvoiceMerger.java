package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.SupplierInvoice;
import org.adorsys.adpharma.server.repo.SupplierInvoiceRepository;

public class SupplierInvoiceMerger
{

   @Inject
   private SupplierInvoiceRepository repository;

   public SupplierInvoice bindComposed(SupplierInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SupplierInvoice bindAggregated(SupplierInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SupplierInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<SupplierInvoice> oldCol = new HashSet<SupplierInvoice>(entities);
      entities.clear();
      for (SupplierInvoice entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SupplierInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<SupplierInvoice> oldCol = new HashSet<SupplierInvoice>(entities);
      entities.clear();
      for (SupplierInvoice entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SupplierInvoice unbind(final SupplierInvoice entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SupplierInvoice newEntity = new SupplierInvoice();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SupplierInvoice> unbind(final Set<SupplierInvoice> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SupplierInvoice>();
      //       HashSet<SupplierInvoice> cache = new HashSet<SupplierInvoice>(entities);
      //       entities.clear();
      //       for (SupplierInvoice entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
