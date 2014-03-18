package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;

@Stateless
public class CustomerInvoiceEJB
{

   @Inject
   private CustomerInvoiceRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CustomerInvoiceItemMerger customerInvoiceItemMerger;

   @Inject
   private SalesOrderMerger salesOrderMerger;

   @Inject
   private InsurranceMerger insurranceMerger;

   @Inject
   private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public CustomerInvoice create(CustomerInvoice entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerInvoice deleteById(Long id)
   {
      CustomerInvoice entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CustomerInvoice update(CustomerInvoice entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerInvoice findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CustomerInvoice> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CustomerInvoice> findBy(CustomerInvoice entity, int start, int max, SingularAttribute<CustomerInvoice, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CustomerInvoice entity, SingularAttribute<CustomerInvoice, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CustomerInvoice> findByLike(CustomerInvoice entity, int start, int max, SingularAttribute<CustomerInvoice, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CustomerInvoice entity, SingularAttribute<CustomerInvoice, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CustomerInvoice attach(CustomerInvoice entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

      // aggregated
      entity.setInsurance(insurranceMerger.bindAggregated(entity.getInsurance()));

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setSalesOrder(salesOrderMerger.bindAggregated(entity.getSalesOrder()));

      // composed collections
      Set<CustomerInvoiceItem> invoiceItems = entity.getInvoiceItems();
      for (CustomerInvoiceItem customerInvoiceItem : invoiceItems)
      {
         customerInvoiceItem.setInvoice(entity);
      }

      // aggregated collection
      paymentCustomerInvoiceAssocMerger.bindAggregated(entity.getPayments());

      return entity;
   }
}
