package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ClearanceConfig;
import org.adorsys.adpharma.server.repo.ClearanceConfigRepository;

@Stateless
public class ClearanceConfigEJB
{

   @Inject
   private ClearanceConfigRepository repository;

   public ClearanceConfig create(ClearanceConfig entity)
   {
      return repository.save(attach(entity));
   }

   public ClearanceConfig deleteById(Long id)
   {
      ClearanceConfig entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ClearanceConfig update(ClearanceConfig entity)
   {
      return repository.save(attach(entity));
   }

   public ClearanceConfig findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ClearanceConfig> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ClearanceConfig> findBy(ClearanceConfig entity, int start, int max, SingularAttribute<ClearanceConfig, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ClearanceConfig entity, SingularAttribute<ClearanceConfig, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ClearanceConfig> findByLike(ClearanceConfig entity, int start, int max, SingularAttribute<ClearanceConfig, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ClearanceConfig entity, SingularAttribute<ClearanceConfig, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ClearanceConfig attach(ClearanceConfig entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
