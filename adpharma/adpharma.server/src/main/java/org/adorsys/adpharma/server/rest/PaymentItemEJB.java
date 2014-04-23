package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.repo.PaymentItemRepository;

@Stateless
public class PaymentItemEJB
{

   @Inject
   private PaymentItemRepository repository;

   @Inject
   private PaymentMerger paymentMerger;

   @Inject
   private CustomerMerger customerMerger;

   public PaymentItem create(PaymentItem entity)
   {
      return repository.save(attach(entity));
   }

   public PaymentItem deleteById(Long id)
   {
      PaymentItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PaymentItem update(PaymentItem entity)
   {
      return repository.save(attach(entity));
   }

   public PaymentItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<PaymentItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PaymentItem> findBy(PaymentItem entity, int start, int max, SingularAttribute<PaymentItem, ?>[] attributes)
   {
	   PaymentItem paymentItem = attach(entity);
      return repository.findBy(paymentItem, start, max, attributes);
   }

   public Long countBy(PaymentItem entity, SingularAttribute<PaymentItem, ?>[] attributes)
   {
	   PaymentItem paymentItem = attach(entity);
      return repository.count(paymentItem, attributes);
   }

   public List<PaymentItem> findByLike(PaymentItem entity, int start, int max, SingularAttribute<PaymentItem, ?>[] attributes)
   {
	   PaymentItem paymentItem = attach(entity);
      return repository.findByLike(paymentItem, start, max, attributes);
   }

   public Long countByLike(PaymentItem entity, SingularAttribute<PaymentItem, ?>[] attributes)
   {
	   PaymentItem paymentItem = attach(entity);
      return repository.countLike(paymentItem, attributes);
   }

   private PaymentItem attach(PaymentItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setPaidBy(customerMerger.bindAggregated(entity.getPaidBy()));

      return entity;
   }
}
