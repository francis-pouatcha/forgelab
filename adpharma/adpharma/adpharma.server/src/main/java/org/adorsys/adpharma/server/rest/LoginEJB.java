package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.LoginRepository;

@Stateless
public class LoginEJB
{

   @Inject
   private LoginRepository repository;

   @Inject
   private LoginRoleNameAssocMerger loginRoleNameAssocMerger;

   public Login create(Login entity)
   {
      return repository.save(attach(entity));
   }

   public Login deleteById(Long id)
   {
      Login entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Login update(Login entity)
   {
      return repository.save(attach(entity));
   }

   public Login findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Login> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Login> findBy(Login entity, int start, int max, SingularAttribute<Login, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Login entity, SingularAttribute<Login, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Login> findByLike(Login entity, int start, int max, SingularAttribute<Login, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Login entity, SingularAttribute<Login, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Login attach(Login entity)
   {
      if (entity == null)
         return null;

      // aggregated collection
      loginRoleNameAssocMerger.bindAggregated(entity.getRoleNames());

      return entity;
   }
}
