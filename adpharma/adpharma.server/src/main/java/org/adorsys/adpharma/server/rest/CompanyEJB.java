package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Company;
import org.adorsys.adpharma.server.repo.CompanyRepository;

@Stateless
public class CompanyEJB
{

   @Inject
   private CompanyRepository repository;

   public Company create(Company entity)
   {
      return repository.save(attach(entity));
   }

   public Company deleteById(Long id)
   {
      Company entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Company update(Company entity)
   {
      return repository.save(attach(entity));
   }

   public Company findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Company> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Company> findBy(Company entity, int start, int max, SingularAttribute<Company, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Company entity, SingularAttribute<Company, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Company> findByLike(Company entity, int start, int max, SingularAttribute<Company, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Company entity, SingularAttribute<Company, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Company attach(Company entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
