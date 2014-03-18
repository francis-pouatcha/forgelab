package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.repo.ArticleRepository;

@Stateless
public class ArticleEJB
{

   @Inject
   private ArticleRepository repository;

   @Inject
   private ClearanceConfigMerger clearanceConfigMerger;

   @Inject
   private SectionMerger sectionMerger;

   @Inject
   private ProductFamilyMerger productFamilyMerger;

   @Inject
   private SalesMarginMerger salesMarginMerger;

   @Inject
   private PackagingModeMerger packagingModeMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Article create(Article entity)
   {
      return repository.save(attach(entity));
   }

   public Article deleteById(Long id)
   {
      Article entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Article update(Article entity)
   {
      return repository.save(attach(entity));
   }

   public Article findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Article> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Article> findBy(Article entity, int start, int max, SingularAttribute<Article, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Article entity, SingularAttribute<Article, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Article> findByLike(Article entity, int start, int max, SingularAttribute<Article, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Article entity, SingularAttribute<Article, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Article attach(Article entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSection(sectionMerger.bindAggregated(entity.getSection()));

      // aggregated
      entity.setFamily(productFamilyMerger.bindAggregated(entity.getFamily()));

      // aggregated
      entity.setDefaultSalesMargin(salesMarginMerger.bindAggregated(entity.getDefaultSalesMargin()));

      // aggregated
      entity.setPackagingMode(packagingModeMerger.bindAggregated(entity.getPackagingMode()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setClearanceConfig(clearanceConfigMerger.bindAggregated(entity.getClearanceConfig()));

      return entity;
   }
}
