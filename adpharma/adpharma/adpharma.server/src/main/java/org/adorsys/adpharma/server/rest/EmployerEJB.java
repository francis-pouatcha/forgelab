package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Employer;
import org.adorsys.adpharma.server.repo.EmployerRepository;

@Stateless
public class EmployerEJB
{

   @Inject
   private EmployerRepository repository;

   @Inject
   private LoginMerger loginMerger;

   public Employer create(Employer entity)
   {
      return repository.save(attach(entity));
   }

   public Employer deleteById(Long id)
   {
      Employer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Employer update(Employer entity)
   {
      return repository.save(attach(entity));
   }

   public Employer findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Employer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Employer> findBy(Employer entity, int start, int max, SingularAttribute<Employer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Employer entity, SingularAttribute<Employer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Employer> findByLike(Employer entity, int start, int max, SingularAttribute<Employer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Employer entity, SingularAttribute<Employer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Employer attach(Employer entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

      return entity;
   }
}
