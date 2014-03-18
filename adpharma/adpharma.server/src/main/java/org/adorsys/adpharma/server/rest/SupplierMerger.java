package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.adorsys.adpharma.server.repo.SupplierRepository;

public class SupplierMerger
{

   @Inject
   private SupplierRepository repository;

   public Supplier bindComposed(Supplier entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Supplier bindAggregated(Supplier entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Supplier> entities)
   {
      if (entities == null)
         return;
      HashSet<Supplier> oldCol = new HashSet<Supplier>(entities);
      entities.clear();
      for (Supplier entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Supplier> entities)
   {
      if (entities == null)
         return;
      HashSet<Supplier> oldCol = new HashSet<Supplier>(entities);
      entities.clear();
      for (Supplier entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Supplier unbind(final Supplier entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Supplier newEntity = new Supplier();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Supplier> unbind(final Set<Supplier> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Supplier>();
      //       HashSet<Supplier> cache = new HashSet<Supplier>(entities);
      //       entities.clear();
      //       for (Supplier entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
