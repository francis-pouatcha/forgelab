package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Disbursement;
import org.adorsys.adpharma.server.repo.DisbursementRepository;

@Stateless
public class DisbursementEJB
{

   @Inject
   private DisbursementRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Disbursement create(Disbursement entity)
   {
      return repository.save(attach(entity));
   }

   public Disbursement deleteById(Long id)
   {
      Disbursement entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Disbursement update(Disbursement entity)
   {
      return repository.save(attach(entity));
   }

   public Disbursement findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Disbursement> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Disbursement> findBy(Disbursement entity, int start, int max, SingularAttribute<Disbursement, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Disbursement entity, SingularAttribute<Disbursement, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Disbursement> findByLike(Disbursement entity, int start, int max, SingularAttribute<Disbursement, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Disbursement entity, SingularAttribute<Disbursement, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Disbursement attach(Disbursement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
