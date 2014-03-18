package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Hospital;
import org.adorsys.adpharma.server.repo.HospitalRepository;

@Stateless
public class HospitalEJB
{

   @Inject
   private HospitalRepository repository;

   public Hospital create(Hospital entity)
   {
      return repository.save(attach(entity));
   }

   public Hospital deleteById(Long id)
   {
      Hospital entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Hospital update(Hospital entity)
   {
      return repository.save(attach(entity));
   }

   public Hospital findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Hospital> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Hospital> findBy(Hospital entity, int start, int max, SingularAttribute<Hospital, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Hospital entity, SingularAttribute<Hospital, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Hospital> findByLike(Hospital entity, int start, int max, SingularAttribute<Hospital, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Hospital entity, SingularAttribute<Hospital, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Hospital attach(Hospital entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
