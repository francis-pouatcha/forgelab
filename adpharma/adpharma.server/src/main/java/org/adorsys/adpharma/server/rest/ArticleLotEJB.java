package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.repo.ArticleLotRepository;

@Stateless
public class ArticleLotEJB
{

   @Inject
   private ArticleLotRepository repository;

   @Inject
   private AgencyMerger agencyMerger;

   @Inject
   private ArticleMerger articleMerger;

   public ArticleLot create(ArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public ArticleLot deleteById(Long id)
   {
      ArticleLot entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ArticleLot update(ArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public ArticleLot findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ArticleLot> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ArticleLot> findBy(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ArticleLot> findByLike(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ArticleLot attach(ArticleLot entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      return entity;
   }
}
