package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.repo.CustomerInvoiceItemRepository;

public class CustomerInvoiceItemMerger
{

   @Inject
   private CustomerInvoiceItemRepository repository;

	@Inject
	private ArticleMerger articleMerger;
   
   public CustomerInvoiceItem bindComposed(CustomerInvoiceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CustomerInvoiceItem bindAggregated(CustomerInvoiceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CustomerInvoiceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoiceItem> oldCol = new HashSet<CustomerInvoiceItem>(entities);
      entities.clear();
      for (CustomerInvoiceItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CustomerInvoiceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<CustomerInvoiceItem> oldCol = new HashSet<CustomerInvoiceItem>(entities);
      entities.clear();
      for (CustomerInvoiceItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CustomerInvoiceItem unbind(final CustomerInvoiceItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CustomerInvoiceItem newEntity = new CustomerInvoiceItem();
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

   public Set<CustomerInvoiceItem> unbind(final Set<CustomerInvoiceItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CustomerInvoiceItem>();
      //       HashSet<CustomerInvoiceItem> cache = new HashSet<CustomerInvoiceItem>(entities);
      //       entities.clear();
      //       for (CustomerInvoiceItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
   
   public Set<CustomerInvoiceItem> unbind1(final Set<CustomerInvoiceItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
//      return new HashSet<CustomerInvoiceItem>();
             HashSet<CustomerInvoiceItem> cache = new HashSet<CustomerInvoiceItem>(entities);
             entities.clear();
             for (CustomerInvoiceItem entity : cache) {
        		entities.add(unbind(entity, fieldList));
             }
            return entities;
   }
   


   private void unbindNested(String fieldName, List<String> nestedFields, CustomerInvoiceItem entity, CustomerInvoiceItem newEntity) {
	   if("article".equals(fieldName)) {
		   newEntity.setArticle(articleMerger.unbind(entity.getArticle(), nestedFields));
	   }
   }
   
}
