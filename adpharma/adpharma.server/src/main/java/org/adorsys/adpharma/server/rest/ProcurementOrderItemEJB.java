package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.repo.ProcurementOrderItemRepository;

@Stateless
public class ProcurementOrderItemEJB
{

   @Inject
   private ProcurementOrderItemRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private ProcurementOrderMerger procurementOrderMerger;

   @Inject
   private ArticleMerger articleMerger;

   public ProcurementOrderItem create(ProcurementOrderItem entity)
   {
      return repository.save(attach(entity));
   }

   public ProcurementOrderItem deleteById(Long id)
   {
      ProcurementOrderItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ProcurementOrderItem update(ProcurementOrderItem entity)
   {
      return repository.save(attach(entity));
   }

   public ProcurementOrderItem findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ProcurementOrderItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ProcurementOrderItem> findBy(ProcurementOrderItem entity, int start, int max, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ProcurementOrderItem entity, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ProcurementOrderItem> findByLike(ProcurementOrderItem entity, int start, int max, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ProcurementOrderItem entity, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ProcurementOrderItem attach(ProcurementOrderItem entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      return entity;
   }
}
