package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc;
import org.adorsys.adpharma.server.repo.LoginRoleNameAssocRepository;

@Stateless
public class LoginRoleNameAssocEJB
{

   @Inject
   private LoginRoleNameAssocRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private RoleNameMerger roleNameMerger;

   public LoginRoleNameAssoc create(LoginRoleNameAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public LoginRoleNameAssoc deleteById(Long id)
   {
      LoginRoleNameAssoc entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public LoginRoleNameAssoc update(LoginRoleNameAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public LoginRoleNameAssoc findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<LoginRoleNameAssoc> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<LoginRoleNameAssoc> findBy(LoginRoleNameAssoc entity, int start, int max, SingularAttribute<LoginRoleNameAssoc, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(LoginRoleNameAssoc entity, SingularAttribute<LoginRoleNameAssoc, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<LoginRoleNameAssoc> findByLike(LoginRoleNameAssoc entity, int start, int max, SingularAttribute<LoginRoleNameAssoc, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(LoginRoleNameAssoc entity, SingularAttribute<LoginRoleNameAssoc, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private LoginRoleNameAssoc attach(LoginRoleNameAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(loginMerger.bindAggregated(entity.getSource()));

      // aggregated
      entity.setTarget(roleNameMerger.bindAggregated(entity.getTarget()));

      return entity;
   }
}
