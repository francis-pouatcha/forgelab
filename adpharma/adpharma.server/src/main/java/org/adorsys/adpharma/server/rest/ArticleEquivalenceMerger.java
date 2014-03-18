package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.ArticleEquivalence;
import org.adorsys.adpharma.server.repo.ArticleEquivalenceRepository;

public class ArticleEquivalenceMerger
{

   @Inject
   private ArticleEquivalenceRepository repository;

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
}
