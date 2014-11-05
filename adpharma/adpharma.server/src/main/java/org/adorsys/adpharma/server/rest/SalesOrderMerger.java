package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;

public class SalesOrderMerger
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
	private InsurranceMerger insurranceMerger;
	
	@Inject
	private VATMerger vATMerger;


  	@Inject
  	private AgencyMerger agencyMerger;

   public SalesOrder bindComposed(SalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SalesOrder bindAggregated(SalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrder> oldCol = new HashSet<SalesOrder>(entities);
      entities.clear();
      for (SalesOrder entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SalesOrder> oldCol = new HashSet<SalesOrder>(entities);
      entities.clear();
      for (SalesOrder entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SalesOrder unbind(final SalesOrder entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SalesOrder newEntity = new SalesOrder();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      
      Map<String, List<String>> nestedFields = MergerUtils.getNestedFields(fieldList);
      Set<String> keySet = nestedFields.keySet();
      for (String fieldName : keySet) {
    	  unbindNested(fieldName, nestedFields.get(fieldName), entity, newEntity);
      }
      
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SalesOrder> unbind(final Set<SalesOrder> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SalesOrder>();
      //       HashSet<SalesOrder> cache = new HashSet<SalesOrder>(entities);
      //       entities.clear();
      //       for (SalesOrder entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
   
   private void unbindNested(String fieldName, List<String> nestedFields, SalesOrder entity, SalesOrder newEntity) {
	   if("cashDrawer".equals(fieldName)) {
		   newEntity.setCashDrawer(cashDrawerMerger.unbind(entity.getCashDrawer(), nestedFields));
	   }
	   if("customer".equals(fieldName)) {
		   newEntity.setCustomer(customerMerger.unbind(entity.getCustomer(), nestedFields));
	   }
	   if("insurance".equals(fieldName)) {
		   newEntity.setInsurance(insurranceMerger.unbind(entity.getInsurance(), nestedFields));
	   }
	   if("vat".equals(fieldName)) {
		   newEntity.setVat(vATMerger.unbind(entity.getVat(), nestedFields));
	   }
	   if("agency".equals(fieldName)) {
		   newEntity.setAgency(agencyMerger.unbind(entity.getAgency(), nestedFields));
	   }
   }
}
