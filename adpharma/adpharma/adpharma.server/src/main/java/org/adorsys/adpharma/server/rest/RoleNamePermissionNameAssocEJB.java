package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc;
import org.adorsys.adpharma.server.repo.RoleNamePermissionNameAssocRepository;

@Stateless
public class RoleNamePermissionNameAssocEJB
{

   @Inject
   private RoleNamePermissionNameAssocRepository repository;

   @Inject
   private RoleNameMerger roleNameMerger;

   @Inject
   private PermissionNameMerger permissionNameMerger;

   public RoleNamePermissionNameAssoc create(RoleNamePermissionNameAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public RoleNamePermissionNameAssoc deleteById(Long id)
   {
      RoleNamePermissionNameAssoc entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public RoleNamePermissionNameAssoc update(RoleNamePermissionNameAssoc entity)
   {
      return repository.save(attach(entity));
   }

   public RoleNamePermissionNameAssoc findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<RoleNamePermissionNameAssoc> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<RoleNamePermissionNameAssoc> findBy(RoleNamePermissionNameAssoc entity, int start, int max, SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(RoleNamePermissionNameAssoc entity, SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<RoleNamePermissionNameAssoc> findByLike(RoleNamePermissionNameAssoc entity, int start, int max, SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(RoleNamePermissionNameAssoc entity, SingularAttribute<RoleNamePermissionNameAssoc, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private RoleNamePermissionNameAssoc attach(RoleNamePermissionNameAssoc entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setSource(roleNameMerger.bindAggregated(entity.getSource()));

      // aggregated
      entity.setTarget(permissionNameMerger.bindAggregated(entity.getTarget()));

      return entity;
   }
}
