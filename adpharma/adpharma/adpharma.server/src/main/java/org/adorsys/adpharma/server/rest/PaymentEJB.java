package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.repo.PaymentRepository;

@Stateless
public class PaymentEJB
{

   @Inject
   private PaymentRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Payment create(Payment entity)
   {
      return repository.save(attach(entity));
   }

   public Payment deleteById(Long id)
   {
      Payment entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Payment update(Payment entity)
   {
      return repository.save(attach(entity));
   }

   public Payment findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Payment> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Payment> findBy(Payment entity, int start, int max, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Payment entity, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Payment> findByLike(Payment entity, int start, int max, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Payment entity, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Payment attach(Payment entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

      // aggregated
      entity.setPaidBy(customerMerger.bindAggregated(entity.getPaidBy()));

      // aggregated collection
      paymentCustomerInvoiceAssocMerger.bindAggregated(entity.getInvoices());

      return entity;
   }
}
