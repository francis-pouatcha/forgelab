package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.ArticleEquivalence;
import org.adorsys.adpharma.server.repo.ArticleEquivalenceRepository;

public class ArticleEquivalenceMerger
{

   @Inject
   private ArticleEquivalenceRepository repository;
   
   @Inject
   private ArticleMerger articleMerger;

   public ArticleEquivalence bindComposed(ArticleEquivalence entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ArticleEquivalence bindAggregated(ArticleEquivalence entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ArticleEquivalence> entities)
   {
      if (entities == null)
         return;
      HashSet<ArticleEquivalence> oldCol = new HashSet<ArticleEquivalence>(entities);
      entities.clear();
      for (ArticleEquivalence entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ArticleEquivalence> entities)
   {
      if (entities == null)
         return;
      HashSet<ArticleEquivalence> oldCol = new HashSet<ArticleEquivalence>(entities);
      entities.clear();
      for (ArticleEquivalence entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ArticleEquivalence unbind(final ArticleEquivalence entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ArticleEquivalence newEntity = new ArticleEquivalence();
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

   public Set<ArticleEquivalence> unbind(final Set<ArticleEquivalence> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ArticleEquivalence>();
      //       HashSet<ArticleEquivalence> cache = new HashSet<ArticleEquivalence>(entities);
      //       entities.clear();
      //       for (ArticleEquivalence entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }

   private void unbindNested(String fieldName, List<String> nestedFields, ArticleEquivalence entity, ArticleEquivalence newEntity) {
	   if("mainArticle".equals(fieldName)) {
		   newEntity.setMainArticle(articleMerger.unbind(entity.getMainArticle(), nestedFields));
	   } else if("equivalentArticle".equals(fieldName)) {
		   newEntity.setEquivalentArticle(articleMerger.unbind(entity.getEquivalentArticle(), nestedFields));
	   }
   }
}
