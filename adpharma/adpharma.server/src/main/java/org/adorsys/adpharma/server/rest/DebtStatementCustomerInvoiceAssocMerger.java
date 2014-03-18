package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.repo.DebtStatementCustomerInvoiceAssocRepository;

public class DebtStatementCustomerInvoiceAssocMerger
{

   @Inject
   private DebtStatementCustomerInvoiceAssocRepository repository;

   public DebtStatementCustomerInvoiceAssoc bindComposed(DebtStatementCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public DebtStatementCustomerInvoiceAssoc bindAggregated(DebtStatementCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<DebtStatementCustomerInvoiceAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<DebtStatementCustomerInvoiceAssoc> oldCol = new HashSet<DebtStatementCustomerInvoiceAssoc>(entities);
      entities.clear();
      for (DebtStatementCustomerInvoiceAssoc entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<DebtStatementCustomerInvoiceAssoc> entities)
   {
      if (entities == null)
         return;
      HashSet<DebtStatementCustomerInvoiceAssoc> oldCol = new HashSet<DebtStatementCustomerInvoiceAssoc>(entities);
      entities.clear();
      for (DebtStatementCustomerInvoiceAssoc entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public DebtStatementCustomerInvoiceAssoc unbind(final DebtStatementCustomerInvoiceAssoc entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      DebtStatementCustomerInvoiceAssoc newEntity = new DebtStatementCustomerInvoiceAssoc();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<DebtStatementCustomerInvoiceAssoc> unbind(final Set<DebtStatementCustomerInvoiceAssoc> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<DebtStatementCustomerInvoiceAssoc>();
      //       HashSet<DebtStatementCustomerInvoiceAssoc> cache = new HashSet<DebtStatementCustomerInvoiceAssoc>(entities);
      //       entities.clear();
      //       for (DebtStatementCustomerInvoiceAssoc entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
