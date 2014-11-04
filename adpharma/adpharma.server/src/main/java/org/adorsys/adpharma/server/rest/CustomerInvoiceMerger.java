package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;

public class CustomerInvoiceMerger
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
   
   public CustomerInvoice bindComposed(CustomerInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CustomerInvoice bindAggregated(CustomerInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CustomerInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoice> oldCol = new HashSet<CustomerInvoice>(entities);
      entities.clear();
      for (CustomerInvoice entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CustomerInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoice> oldCol = new HashSet<CustomerInvoice>(entities);
      entities.clear();
      for (CustomerInvoice entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CustomerInvoice unbind(final CustomerInvoice entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CustomerInvoice newEntity = new CustomerInvoice();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      
      
      Map<String, List<String>> nestedFields = MergerUtils.getNestedFields(fieldList);
      Set<String> keySet = nestedFields.keySet();
      for (String fieldName : keySet) {
    	  unbindNested(fieldName, nestedFields.get(fieldName), entity, newEntity);
      }
      
      MergerUtils.copyFields(entity, newEntity, fieldList);
      newEntity.setInsurance(entity.getInsurance());
      newEntity.setCustomer(entity.getCustomer());
      SalesOrder salesOrder = new SalesOrder();
      if(entity.getSalesOrder()!=null){
      salesOrder.setId(entity.getSalesOrder().getId());
      salesOrder.setSoNumber(entity.getSalesOrder().getSoNumber());
      newEntity.setSalesOrder(salesOrder);
      }
      return newEntity;
   }

   public Set<CustomerInvoice> unbind(final Set<CustomerInvoice> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CustomerInvoice>();
      //       HashSet<CustomerInvoice> cache = new HashSet<CustomerInvoice>(entities);
      //       entities.clear();
      //       for (CustomerInvoice entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }

   private void unbindNested(String fieldName, List<String> nestedFields, CustomerInvoice entity, CustomerInvoice newEntity) {
	   if("insurance".equals(fieldName)) {
		   newEntity.setInsurance(insurranceMerger.unbind(entity.getInsurance(), nestedFields));
	   } else if("customer".equals(fieldName)) {
		   newEntity.setCustomer(customerMerger.unbind(entity.getCustomer(), nestedFields));
	   } else if("salesOrder".equals(fieldName)) {
		   newEntity.setSalesOrder(salesOrderMerger.unbind(entity.getSalesOrder(), nestedFields));
	   } else if("invoiceItems".equals(fieldName)) {
		   Set<CustomerInvoiceItem> newInvoiceItems = new HashSet<CustomerInvoiceItem>();
		   Set<CustomerInvoiceItem> invoiceItems = entity.getInvoiceItems();
		   for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			   newInvoiceItems.add(customerInvoiceItemMerger.unbind(customerInvoiceItem, nestedFields));
		   }
		   newEntity.setInvoiceItems(newInvoiceItems);
	   }
   }
}
