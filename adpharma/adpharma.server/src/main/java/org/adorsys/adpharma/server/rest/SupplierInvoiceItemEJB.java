package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import org.adorsys.adpharma.server.repo.SupplierInvoiceItemRepository;

@Stateless
public class SupplierInvoiceItemEJB
{

   @Inject
   private SupplierInvoiceItemRepository repository;

   @Inject
   private SupplierInvoiceMerger supplierInvoiceMerger;

   @Inject
   private ArticleMerger articleMerger;

   public SupplierInvoiceItem create(SupplierInvoiceItem entity)
   {
      return repository.save(attach(entity));
   }

   public SupplierInvoiceItem deleteById(Long id)
   {
      SupplierInvoiceItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SupplierInvoiceItem update(SupplierInvoiceItem entity)
   {
      return repository.save(attach(entity));
   }

   public SupplierInvoiceItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SupplierInvoiceItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SupplierInvoiceItem> findBy(SupplierInvoiceItem entity, int start, int max, SingularAttribute<SupplierInvoiceItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SupplierInvoiceItem entity, SingularAttribute<SupplierInvoiceItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SupplierInvoiceItem> findByLike(SupplierInvoiceItem entity, int start, int max, SingularAttribute<SupplierInvoiceItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SupplierInvoiceItem entity, SingularAttribute<SupplierInvoiceItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SupplierInvoiceItem attach(SupplierInvoiceItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      return entity;
   }
}
