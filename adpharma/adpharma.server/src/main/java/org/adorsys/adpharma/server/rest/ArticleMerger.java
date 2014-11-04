package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleEquivalence;
import org.adorsys.adpharma.server.repo.ArticleRepository;

public class ArticleMerger
{

   @Inject
   private ArticleRepository repository;
   
   @Inject
   private ProductFamilyMerger productFamilyMerger;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private SalesMarginMerger salesMarginMerger;

   @Inject
   private VATMerger vATMerger;

   @Inject
   private PackagingModeMerger packagingModeMerger;

   @Inject
   private SectionMerger sectionMerger;

   @Inject
   private ClearanceConfigMerger clearanceConfigMerger;
   

   public Article bindComposed(Article entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Article bindAggregated(Article entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Article> entities)
   {
      if (entities == null)
         return;
      HashSet<Article> oldCol = new HashSet<Article>(entities);
      entities.clear();
      for (Article entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Article> entities)
   {
      if (entities == null)
         return;
      HashSet<Article> oldCol = new HashSet<Article>(entities);
      entities.clear();
      for (Article entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Article unbind(final Article entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Article newEntity = new Article();
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

   public Set<Article> unbind(final Set<Article> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Article>();
      //       HashSet<Article> cache = new HashSet<Article>(entities);
      //       entities.clear();
      //       for (Article entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }

   private void unbindNested(String fieldName, List<String> nestedFields, Article entity, Article newEntity) {
	   if("section".equals(fieldName)) {
		   newEntity.setSection(sectionMerger.unbind(entity.getSection(), nestedFields));
	   } else if("family".equals(fieldName)) {
		   newEntity.setFamily(productFamilyMerger.unbind(entity.getFamily(), nestedFields));
	   } else if("defaultSalesMargin".equals(fieldName)) {
		   newEntity.setDefaultSalesMargin(salesMarginMerger.unbind(entity.getDefaultSalesMargin(), nestedFields));
	   } else if("packagingMode".equals(fieldName)) {
		   newEntity.setPackagingMode(packagingModeMerger.unbind(entity.getPackagingMode(), nestedFields));
	   } else if("agency".equals(fieldName)) {
		   newEntity.setAgency(agencyMerger.unbind(entity.getAgency(), nestedFields));
	   } else if("clearanceConfig".equals(fieldName)) {
		   newEntity.setClearanceConfig(clearanceConfigMerger.unbind(entity.getClearanceConfig(), nestedFields));
	   } else if("vat".equals(fieldName)) {
		   newEntity.setVat(vATMerger.unbind(entity.getVat(), nestedFields));
	   }
   }
}
