package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.repo.PaymentCustomerInvoiceAssocRepository;

@Stateless
public class PaymentCustomerInvoiceAssocEJB
{

   @Inject
   private PaymentCustomerInvoiceAssocRepository repository;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;

   @Inject
   private PaymentMerger paymentMerger;

   public PaymentCustomerInvoiceAssoc create(PaymentCustomerInvoiceAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public PaymentCustomerInvoiceAssoc deleteById(Long id)
   {
      PaymentCustomerInvoiceAssoc entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PaymentCustomerInvoiceAssoc update(PaymentCustomerInvoiceAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public PaymentCustomerInvoiceAssoc findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<PaymentCustomerInvoiceAssoc> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PaymentCustomerInvoiceAssoc> findBy(PaymentCustomerInvoiceAssoc entity, int start, int max, SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PaymentCustomerInvoiceAssoc entity, SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PaymentCustomerInvoiceAssoc> findByLike(PaymentCustomerInvoiceAssoc entity, int start, int max, SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PaymentCustomerInvoiceAssoc entity, SingularAttribute<PaymentCustomerInvoiceAssoc, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PaymentCustomerInvoiceAssoc attach(PaymentCustomerInvoiceAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(paymentMerger.bindAggregated(entity.getSource()));

      // aggregated
      entity.setTarget(customerInvoiceMerger.bindAggregated(entity.getTarget()));

      return entity;
   }
}
