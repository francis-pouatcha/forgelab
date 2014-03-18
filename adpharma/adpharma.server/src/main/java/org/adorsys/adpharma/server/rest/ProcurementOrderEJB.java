package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.repo.ProcurementOrderRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;

@Stateless
public class ProcurementOrderEJB
{

   @Inject
   private ProcurementOrderRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private ProcurementOrderItemMerger procurementOrderItemMerger;

   @Inject
   private SupplierMerger supplierMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public ProcurementOrder create(ProcurementOrder entity)
   {
      return repository.save(attach(entity));
   }

   public ProcurementOrder deleteById(Long id)
   {
      ProcurementOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ProcurementOrder update(ProcurementOrder entity)
   {
      return repository.save(attach(entity));
   }

   public ProcurementOrder findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ProcurementOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ProcurementOrder> findBy(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ProcurementOrder> findByLike(ProcurementOrder entity, int start, int max, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ProcurementOrder entity, SingularAttribute<ProcurementOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ProcurementOrder attach(ProcurementOrder entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      // composed collections
      Set<ProcurementOrderItem> procurementOrderItems = entity.getProcurementOrderItems();
      for (ProcurementOrderItem procurementOrderItem : procurementOrderItems)
      {
         procurementOrderItem.setProcurementOrder(entity);
      }

      return entity;
   }
}
