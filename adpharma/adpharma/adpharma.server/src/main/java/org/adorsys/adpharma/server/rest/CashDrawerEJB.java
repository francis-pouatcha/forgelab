package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.repo.CashDrawerRepository;

@Stateless
public class CashDrawerEJB
{

   @Inject
   private CashDrawerRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public CashDrawer create(CashDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CashDrawer deleteById(Long id)
   {
      CashDrawer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CashDrawer update(CashDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CashDrawer findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CashDrawer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CashDrawer> findBy(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CashDrawer> findByLike(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CashDrawer attach(CashDrawer entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

      // aggregated
      entity.setClosedBy(loginMerger.bindAggregated(entity.getClosedBy()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
