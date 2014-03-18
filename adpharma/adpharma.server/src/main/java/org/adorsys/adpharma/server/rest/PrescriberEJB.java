package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Prescriber;
import org.adorsys.adpharma.server.repo.PrescriberRepository;

@Stateless
public class PrescriberEJB
{

   @Inject
   private PrescriberRepository repository;

   public Prescriber create(Prescriber entity)
   {
      return repository.save(attach(entity));
   }

   public Prescriber deleteById(Long id)
   {
      Prescriber entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Prescriber update(Prescriber entity)
   {
      return repository.save(attach(entity));
   }

   public Prescriber findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Prescriber> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Prescriber> findBy(Prescriber entity, int start, int max, SingularAttribute<Prescriber, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Prescriber entity, SingularAttribute<Prescriber, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Prescriber> findByLike(Prescriber entity, int start, int max, SingularAttribute<Prescriber, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Prescriber entity, SingularAttribute<Prescriber, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Prescriber attach(Prescriber entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
