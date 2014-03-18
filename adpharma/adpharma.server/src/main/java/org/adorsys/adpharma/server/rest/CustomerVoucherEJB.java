package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.CustomerVoucher;
import org.adorsys.adpharma.server.repo.CustomerVoucherRepository;

@Stateless
public class CustomerVoucherEJB
{

   @Inject
   private CustomerVoucherRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public CustomerVoucher create(CustomerVoucher entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerVoucher deleteById(Long id)
   {
      CustomerVoucher entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CustomerVoucher update(CustomerVoucher entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerVoucher findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CustomerVoucher> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CustomerVoucher> findBy(CustomerVoucher entity, int start, int max, SingularAttribute<CustomerVoucher, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CustomerVoucher entity, SingularAttribute<CustomerVoucher, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CustomerVoucher> findByLike(CustomerVoucher entity, int start, int max, SingularAttribute<CustomerVoucher, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CustomerVoucher entity, SingularAttribute<CustomerVoucher, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CustomerVoucher attach(CustomerVoucher entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCustomerInvoice(customerInvoiceMerger.bindAggregated(entity.getCustomerInvoice()));

      // aggregated
      entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

      return entity;
   }
}
