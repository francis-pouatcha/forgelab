package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.PermissionName;
import org.adorsys.adpharma.server.repo.PermissionNameRepository;

@Stateless
public class PermissionNameEJB
{

   @Inject
   private PermissionNameRepository repository;

   public PermissionName create(PermissionName entity)
   {
      return repository.save(attach(entity));
   }

   public PermissionName deleteById(Long id)
   {
      PermissionName entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PermissionName update(PermissionName entity)
   {
      return repository.save(attach(entity));
   }

   public PermissionName findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<PermissionName> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PermissionName> findBy(PermissionName entity, int start, int max, SingularAttribute<PermissionName, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PermissionName entity, SingularAttribute<PermissionName, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PermissionName> findByLike(PermissionName entity, int start, int max, SingularAttribute<PermissionName, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PermissionName entity, SingularAttribute<PermissionName, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PermissionName attach(PermissionName entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
