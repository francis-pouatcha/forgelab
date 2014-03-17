package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.repo.DebtStatementCustomerInvoiceAssocRepository;

@Stateless
public class DebtStatementCustomerInvoiceAssocEJB
{

   @Inject
   private DebtStatementCustomerInvoiceAssocRepository repository;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;

   @Inject
   private DebtStatementMerger debtStatementMerger;

   public DebtStatementCustomerInvoiceAssoc create(DebtStatementCustomerInvoiceAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public DebtStatementCustomerInvoiceAssoc deleteById(Long id)
   {
      DebtStatementCustomerInvoiceAssoc entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public DebtStatementCustomerInvoiceAssoc update(DebtStatementCustomerInvoiceAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public DebtStatementCustomerInvoiceAssoc findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<DebtStatementCustomerInvoiceAssoc> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<DebtStatementCustomerInvoiceAssoc> findBy(DebtStatementCustomerInvoiceAssoc entity, int start, int max, SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DebtStatementCustomerInvoiceAssoc entity, SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DebtStatementCustomerInvoiceAssoc> findByLike(DebtStatementCustomerInvoiceAssoc entity, int start, int max, SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DebtStatementCustomerInvoiceAssoc entity, SingularAttribute<DebtStatementCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DebtStatementCustomerInvoiceAssoc attach(DebtStatementCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(debtStatementMerger.bindAggregated(entity.getSource()));

      // aggregated
      entity.setTarget(customerInvoiceMerger.bindAggregated(entity.getTarget()));

      return entity;
   }
}
