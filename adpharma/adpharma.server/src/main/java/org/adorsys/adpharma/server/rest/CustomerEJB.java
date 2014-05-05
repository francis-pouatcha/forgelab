package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.EJB;
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
   
   @EJB
   private CustomerEJB customerEJB;

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
	   Customer customer = attach(entity);
      return repository.findBy(customer, start, max, attributes);
   }

   public Long countBy(Customer entity, SingularAttribute<Customer, ?>[] attributes)
   {
	   Customer customer = attach(entity);
      return repository.count(customer, attributes);
   }

   public List<Customer> findByLike(Customer entity, int start, int max, SingularAttribute<Customer, ?>[] attributes)
   {
	   Customer customer = attach(entity);
      return repository.findByLike(customer, start, max, attributes);
   }

   public Long countByLike(Customer entity, SingularAttribute<Customer, ?>[] attributes)
   {
	   Customer customer = attach(entity);
      return repository.countLike(customer, attributes);
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
   
   @SuppressWarnings("unchecked")
   public Customer otherCustomers(){
	   List<Customer> found = findBy(CustomerEJBOtherClientsHelper.searchInput, 0, 1, CustomerEJBOtherClientsHelper.attributes);
	   if(found.isEmpty()) throw new IllegalStateException("Application not yet initialized.");
	   return found.iterator().next();
   }
   
}
