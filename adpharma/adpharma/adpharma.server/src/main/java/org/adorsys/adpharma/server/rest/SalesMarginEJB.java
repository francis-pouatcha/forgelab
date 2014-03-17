package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.SalesMargin;
import org.adorsys.adpharma.server.repo.SalesMarginRepository;

@Stateless
public class SalesMarginEJB
{

   @Inject
   private SalesMarginRepository repository;

   public SalesMargin create(SalesMargin entity)
   {
      return repository.save(attach(entity));
   }

   public SalesMargin deleteById(Long id)
   {
      SalesMargin entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SalesMargin update(SalesMargin entity)
   {
      return repository.save(attach(entity));
   }

   public SalesMargin findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<SalesMargin> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SalesMargin> findBy(SalesMargin entity, int start, int max, SingularAttribute<SalesMargin, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SalesMargin entity, SingularAttribute<SalesMargin, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SalesMargin> findByLike(SalesMargin entity, int start, int max, SingularAttribute<SalesMargin, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SalesMargin entity, SingularAttribute<SalesMargin, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SalesMargin attach(SalesMargin entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
