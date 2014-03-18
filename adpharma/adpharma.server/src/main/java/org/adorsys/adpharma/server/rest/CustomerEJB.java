package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.repo.CustomerRepository;

@Stateless
public class CustomerEJB
{

   @Inject
   private CustomerRepository repository;

   @Inject
   private EmployerMerger employerMerger;

   @Inject
   private CustomerCategoryMerger customerCategoryMerger;

   public Customer create(Customer entity)
   {
      return repository.save(attach(entity));
   }

   public Customer deleteById(Long id)
   {
      Customer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Customer update(Customer entity)
   {
      return repository.save(attach(entity));
   }

   public Customer findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Customer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Customer> findBy(Customer entity, int start, int max, SingularAttribute<Customer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Customer entity, SingularAttribute<Customer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Customer> findByLike(Customer entity, int start, int max, SingularAttribute<Customer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Customer entity, SingularAttribute<Customer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Customer attach(Customer entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setEmployer(employerMerger.bindAggregated(entity.getEmployer()));

      // aggregated
      entity.setCustomerCategory(customerCategoryMerger.bindAggregated(entity.getCustomerCategory()));

      return entity;
   }
}
