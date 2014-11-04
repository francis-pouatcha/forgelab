package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.repo.ArticleLotRepository;

public class ArticleLotMerger
{

   @Inject
   private ArticleLotRepository repository;
   
	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private VATMerger vATMerger;
   

   public ArticleLot bindComposed(ArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ArticleLot bindAggregated(ArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<ArticleLot> oldCol = new HashSet<ArticleLot>(entities);
      entities.clear();
      for (ArticleLot entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<ArticleLot> oldCol = new HashSet<ArticleLot>(entities);
      entities.clear();
      for (ArticleLot entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ArticleLot unbind(final ArticleLot entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ArticleLot newEntity = new ArticleLot();
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

   public Set<ArticleLot> unbind(final Set<ArticleLot> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ArticleLot>();
      //       HashSet<ArticleLot> cache = new HashSet<ArticleLot>(entities);
      //       entities.clear();
      //       for (ArticleLot entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }


   private void unbindNested(String fieldName, List<String> nestedFields, ArticleLot entity, ArticleLot newEntity) {
	   if("article".equals(fieldName)) {
		   newEntity.setArticle(articleMerger.unbind(entity.getArticle(), nestedFields));
	   } else if("agency".equals(fieldName)) {
		   newEntity.setAgency(agencyMerger.unbind(entity.getAgency(), nestedFields));
	   } else if("vat".equals(fieldName)) {
		   newEntity.setVat(vATMerger.unbind(entity.getVat(), nestedFields));
	   }
   }
}
