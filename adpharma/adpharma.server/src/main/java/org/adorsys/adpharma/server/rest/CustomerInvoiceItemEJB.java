package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.ProcurementOrderPreparationData;
import org.adorsys.adpharma.server.repo.CustomerInvoiceItemRepository;

@Stateless
public class CustomerInvoiceItemEJB
{

   @Inject
   private CustomerInvoiceItemRepository repository;

   @Inject
   private CustomerInvoiceMerger customerInvoiceMerger;
   
   @Inject
   private ArticleMerger articleMerger;
   
   public List<CustomerInvoiceItem> findPreparationDataItem(ProcurementOrderPreparationData data){
	   return repository.findPreparationDataItem(data.getFromDate(), data.getToDate(), true);
   }

   public CustomerInvoiceItem create(CustomerInvoiceItem entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerInvoiceItem deleteById(Long id)
   {
      CustomerInvoiceItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CustomerInvoiceItem update(CustomerInvoiceItem entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerInvoiceItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CustomerInvoiceItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CustomerInvoiceItem> findBy(CustomerInvoiceItem entity, int start, int max, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CustomerInvoiceItem entity, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CustomerInvoiceItem> findByLike(CustomerInvoiceItem entity, int start, int max, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CustomerInvoiceItem entity, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CustomerInvoiceItem attach(CustomerInvoiceItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      return entity;
   }
}
