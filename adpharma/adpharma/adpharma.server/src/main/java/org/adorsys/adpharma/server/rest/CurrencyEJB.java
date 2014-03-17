package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Currency;
import org.adorsys.adpharma.server.repo.CurrencyRepository;

@Stateless
public class CurrencyEJB
{

   @Inject
   private CurrencyRepository repository;

   public Currency create(Currency entity)
   {
      return repository.save(attach(entity));
   }

   public Currency deleteById(Long id)
   {
      Currency entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Currency update(Currency entity)
   {
      return repository.save(attach(entity));
   }

   public Currency findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Currency> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Currency> findBy(Currency entity, int start, int max, SingularAttribute<Currency, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Currency entity, SingularAttribute<Currency, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Currency> findByLike(Currency entity, int start, int max, SingularAttribute<Currency, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Currency entity, SingularAttribute<Currency, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Currency attach(Currency entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
