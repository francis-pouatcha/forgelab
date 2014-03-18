package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.RoleName;
import org.adorsys.adpharma.server.repo.RoleNameRepository;

@Stateless
public class RoleNameEJB
{

   @Inject
   private RoleNameRepository repository;

   @Inject
   private RoleNamePermissionNameAssocMerger roleNamePermissionNameAssocMerger;

   public RoleName create(RoleName entity)
   {
      return repository.save(attach(entity));
   }

   public RoleName deleteById(Long id)
   {
      RoleName entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public RoleName update(RoleName entity)
   {
      return repository.save(attach(entity));
   }

   public RoleName findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<RoleName> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<RoleName> findBy(RoleName entity, int start, int max, SingularAttribute<RoleName, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(RoleName entity, SingularAttribute<RoleName, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<RoleName> findByLike(RoleName entity, int start, int max, SingularAttribute<RoleName, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(RoleName entity, SingularAttribute<RoleName, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private RoleName attach(RoleName entity)
   {
      if (entity == null)
         return null;

      // aggregated collection
      roleNamePermissionNameAssocMerger.bindAggregated(entity.getPermissions());

      return entity;
   }
}
