package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import org.adorsys.adpharma.server.repo.SupplierInvoiceItemRepository;

public class SupplierInvoiceItemMerger
{

   @Inject
   private SupplierInvoiceItemRepository repository;

   public SupplierInvoiceItem bindComposed(SupplierInvoiceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SupplierInvoiceItem bindAggregated(SupplierInvoiceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SupplierInvoiceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SupplierInvoiceItem> oldCol = new HashSet<SupplierInvoiceItem>(entities);
      entities.clear();
      for (SupplierInvoiceItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SupplierInvoiceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SupplierInvoiceItem> oldCol = new HashSet<SupplierInvoiceItem>(entities);
      entities.clear();
      for (SupplierInvoiceItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SupplierInvoiceItem unbind(final SupplierInvoiceItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SupplierInvoiceItem newEntity = new SupplierInvoiceItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SupplierInvoiceItem> unbind(final Set<SupplierInvoiceItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SupplierInvoiceItem>();
      //       HashSet<SupplierInvoiceItem> cache = new HashSet<SupplierInvoiceItem>(entities);
      //       entities.clear();
      //       for (SupplierInvoiceItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
