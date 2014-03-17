package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.SupplierInvoice;
import org.adorsys.adpharma.server.repo.SupplierInvoiceRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;

@Stateless
public class SupplierInvoiceEJB
{

   @Inject
   private SupplierInvoiceRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SupplierMerger supplierMerger;

   @Inject
   private DeliveryMerger deliveryMerger;

   @Inject
   private SupplierInvoiceItemMerger supplierInvoiceItemMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public SupplierInvoice create(SupplierInvoice entity)
   {
      return repository.save(attach(entity));
   }

   public SupplierInvoice deleteById(Long id)
   {
      SupplierInvoice entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SupplierInvoice update(SupplierInvoice entity)
   {
      return repository.save(attach(entity));
   }

   public SupplierInvoice findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SupplierInvoice> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SupplierInvoice> findBy(SupplierInvoice entity, int start, int max, SingularAttribute<SupplierInvoice, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SupplierInvoice entity, SingularAttribute<SupplierInvoice, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SupplierInvoice> findByLike(SupplierInvoice entity, int start, int max, SingularAttribute<SupplierInvoice, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SupplierInvoice entity, SingularAttribute<SupplierInvoice, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SupplierInvoice attach(SupplierInvoice entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setDelivery(deliveryMerger.bindAggregated(entity.getDelivery()));

      // composed collections
      Set<SupplierInvoiceItem> invoiceItems = entity.getInvoiceItems();
      for (SupplierInvoiceItem supplierInvoiceItem : invoiceItems)
      {
         supplierInvoiceItem.setInvoice(entity);
      }

      return entity;
   }
}
