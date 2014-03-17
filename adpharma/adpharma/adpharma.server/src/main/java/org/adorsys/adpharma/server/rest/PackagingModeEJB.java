package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.PackagingMode;
import org.adorsys.adpharma.server.repo.PackagingModeRepository;

@Stateless
public class PackagingModeEJB
{

   @Inject
   private PackagingModeRepository repository;

   public PackagingMode create(PackagingMode entity)
   {
      return repository.save(attach(entity));
   }

   public PackagingMode deleteById(Long id)
   {
      PackagingMode entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PackagingMode update(PackagingMode entity)
   {
      return repository.save(attach(entity));
   }

   public PackagingMode findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<PackagingMode> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PackagingMode> findBy(PackagingMode entity, int start, int max, SingularAttribute<PackagingMode, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PackagingMode entity, SingularAttribute<PackagingMode, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PackagingMode> findByLike(PackagingMode entity, int start, int max, SingularAttribute<PackagingMode, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PackagingMode entity, SingularAttribute<PackagingMode, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PackagingMode attach(PackagingMode entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
