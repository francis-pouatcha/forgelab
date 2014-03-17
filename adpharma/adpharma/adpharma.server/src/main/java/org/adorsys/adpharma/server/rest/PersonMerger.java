package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Person;
import org.adorsys.adpharma.server.repo.PersonRepository;

public class PersonMerger
{

   @Inject
   private PersonRepository repository;

   public Person bindComposed(Person entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Person bindAggregated(Person entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Person> entities)
   {
      if (entities == null)
         return;
      HashSet<Person> oldCol = new HashSet<Person>(entities);
      entities.clear();
      for (Person entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Person> entities)
   {
      if (entities == null)
         return;
      HashSet<Person> oldCol = new HashSet<Person>(entities);
      entities.clear();
      for (Person entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Person unbind(final Person entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Person newEntity = new Person();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Person> unbind(final Set<Person> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Person>();
      //       HashSet<Person> cache = new HashSet<Person>(entities);
      //       entities.clear();
      //       for (Person entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
