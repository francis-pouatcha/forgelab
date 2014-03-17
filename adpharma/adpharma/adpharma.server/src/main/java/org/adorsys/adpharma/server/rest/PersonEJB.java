package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Person;
import org.adorsys.adpharma.server.repo.PersonRepository;

@Stateless
public class PersonEJB
{

   @Inject
   private PersonRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public Person create(Person entity)
   {
      return repository.save(attach(entity));
   }

   public Person deleteById(Long id)
   {
      Person entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Person update(Person entity)
   {
      return repository.save(attach(entity));
   }

   public Person findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Person> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Person> findBy(Person entity, int start, int max, SingularAttribute<Person, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Person entity, SingularAttribute<Person, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Person> findByLike(Person entity, int start, int max, SingularAttribute<Person, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Person entity, SingularAttribute<Person, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Person attach(Person entity)
   {
      if (entity == null)
         return null;

      // composed

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
