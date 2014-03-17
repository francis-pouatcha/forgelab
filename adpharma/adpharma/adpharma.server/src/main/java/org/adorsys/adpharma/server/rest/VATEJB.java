package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.VATRepository;

@Stateless
public class VATEJB
{

   @Inject
   private VATRepository repository;

   public VAT create(VAT entity)
   {
      return repository.save(attach(entity));
   }

   public VAT deleteById(Long id)
   {
      VAT entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public VAT update(VAT entity)
   {
      return repository.save(attach(entity));
   }

   public VAT findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<VAT> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<VAT> findBy(VAT entity, int start, int max, SingularAttribute<VAT, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(VAT entity, SingularAttribute<VAT, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<VAT> findByLike(VAT entity, int start, int max, SingularAttribute<VAT, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(VAT entity, SingularAttribute<VAT, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private VAT attach(VAT entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
