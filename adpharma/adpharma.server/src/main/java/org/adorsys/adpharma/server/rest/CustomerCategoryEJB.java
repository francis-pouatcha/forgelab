package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.CustomerCategory;
import org.adorsys.adpharma.server.repo.CustomerCategoryRepository;

@Stateless
public class CustomerCategoryEJB
{

   @Inject
   private CustomerCategoryRepository repository;

   public CustomerCategory create(CustomerCategory entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerCategory deleteById(Long id)
   {
      CustomerCategory entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CustomerCategory update(CustomerCategory entity)
   {
      return repository.save(attach(entity));
   }

   public CustomerCategory findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CustomerCategory> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CustomerCategory> findBy(CustomerCategory entity, int start, int max, SingularAttribute<CustomerCategory, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CustomerCategory entity, SingularAttribute<CustomerCategory, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CustomerCategory> findByLike(CustomerCategory entity, int start, int max, SingularAttribute<CustomerCategory, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CustomerCategory entity, SingularAttribute<CustomerCategory, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CustomerCategory attach(CustomerCategory entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
