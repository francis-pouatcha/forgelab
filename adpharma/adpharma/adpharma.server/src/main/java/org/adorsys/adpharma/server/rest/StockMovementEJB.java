package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.repo.StockMovementRepository;

@Stateless
public class StockMovementEJB
{

   @Inject
   private StockMovementRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private ArticleMerger articleMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public StockMovement create(StockMovement entity)
   {
      return repository.save(attach(entity));
   }

   public StockMovement deleteById(Long id)
   {
      StockMovement entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StockMovement update(StockMovement entity)
   {
      return repository.save(attach(entity));
   }

   public StockMovement findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<StockMovement> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StockMovement> findBy(StockMovement entity, int start, int max, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StockMovement> findByLike(StockMovement entity, int start, int max, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StockMovement attach(StockMovement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
