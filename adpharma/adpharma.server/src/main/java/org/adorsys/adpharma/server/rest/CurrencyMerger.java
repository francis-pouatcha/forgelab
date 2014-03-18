package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Currency;
import org.adorsys.adpharma.server.repo.CurrencyRepository;

public class CurrencyMerger
{

   @Inject
   private CurrencyRepository repository;

   public Currency bindComposed(Currency entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Currency bindAggregated(Currency entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Currency> entities)
   {
      if (entities == null)
         return;
      HashSet<Currency> oldCol = new HashSet<Currency>(entities);
      entities.clear();
      for (Currency entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Currency> entities)
   {
      if (entities == null)
         return;
      HashSet<Currency> oldCol = new HashSet<Currency>(entities);
      entities.clear();
      for (Currency entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Currency unbind(final Currency entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Currency newEntity = new Currency();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Currency> unbind(final Set<Currency> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Currency>();
      //       HashSet<Currency> cache = new HashSet<Currency>(entities);
      //       entities.clear();
      //       for (Currency entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
