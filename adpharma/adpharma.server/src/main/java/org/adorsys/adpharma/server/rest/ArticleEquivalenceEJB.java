package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ArticleEquivalence;
import org.adorsys.adpharma.server.repo.ArticleEquivalenceRepository;

@Stateless
public class ArticleEquivalenceEJB
{

   @Inject
   private ArticleEquivalenceRepository repository;

   @Inject
   private ArticleMerger articleMerger;

   public ArticleEquivalence create(ArticleEquivalence entity)
   {
      return repository.save(attach(entity));
   }

   public ArticleEquivalence deleteById(Long id)
   {
      ArticleEquivalence entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ArticleEquivalence update(ArticleEquivalence entity)
   {
      return repository.save(attach(entity));
   }

   public ArticleEquivalence findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ArticleEquivalence> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ArticleEquivalence> findBy(ArticleEquivalence entity, int start, int max, SingularAttribute<ArticleEquivalence, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ArticleEquivalence entity, SingularAttribute<ArticleEquivalence, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ArticleEquivalence> findByLike(ArticleEquivalence entity, int start, int max, SingularAttribute<ArticleEquivalence, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ArticleEquivalence entity, SingularAttribute<ArticleEquivalence, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ArticleEquivalence attach(ArticleEquivalence entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setMainArticle(articleMerger.bindAggregated(entity.getMainArticle()));

      // aggregated
      entity.setEquivalentArticle(articleMerger.bindAggregated(entity.getEquivalentArticle()));

      return entity;
   }
}
