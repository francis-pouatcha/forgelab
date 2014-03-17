package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.repo.DebtStatementRepository;

@Stateless
public class DebtStatementEJB
{

   @Inject
   private DebtStatementRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private DebtStatementCustomerInvoiceAssocMerger debtStatementCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public DebtStatement create(DebtStatement entity)
   {
      return repository.save(attach(entity));
   }

   public DebtStatement deleteById(Long id)
   {
      DebtStatement entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public DebtStatement update(DebtStatement entity)
   {
      return repository.save(attach(entity));
   }

   public DebtStatement findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<DebtStatement> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<DebtStatement> findBy(DebtStatement entity, int start, int max, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DebtStatement entity, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DebtStatement> findByLike(DebtStatement entity, int start, int max, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DebtStatement entity, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DebtStatement attach(DebtStatement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setInsurrance(customerMerger.bindAggregated(entity.getInsurrance()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated collection
      debtStatementCustomerInvoiceAssocMerger.bindAggregated(entity.getInvoices());

      return entity;
   }
}
