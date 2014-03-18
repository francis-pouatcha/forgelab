package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.repo.ArticleRepository;

public class ArticleMerger
{

   @Inject
   private ArticleRepository repository;

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
}
