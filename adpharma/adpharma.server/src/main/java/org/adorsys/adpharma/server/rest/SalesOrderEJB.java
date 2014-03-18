package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;
import java.util.Set;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;

@Stateless
public class SalesOrderEJB
{

   @Inject
   private SalesOrderRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private InsurranceMerger insurranceMerger;

   @Inject
   private SalesOrderItemMerger salesOrderItemMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public SalesOrder create(SalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SalesOrder deleteById(Long id)
   {
      SalesOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SalesOrder update(SalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SalesOrder findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SalesOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SalesOrder> findBy(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SalesOrder> findByLike(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SalesOrder attach(SalesOrder entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

      // aggregated
      entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

      // aggregated
      entity.setInsurance(insurranceMerger.bindAggregated(entity.getInsurance()));

      // aggregated
      entity.setVat(vATMerger.bindAggregated(entity.getVat()));

      // aggregated
      entity.setSalesAgent(loginMerger.bindAggregated(entity.getSalesAgent()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // composed collections
      Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
      for (SalesOrderItem salesOrderItem : salesOrderItems)
      {
         salesOrderItem.setSalesOrder(entity);
      }

      return entity;
   }
}
