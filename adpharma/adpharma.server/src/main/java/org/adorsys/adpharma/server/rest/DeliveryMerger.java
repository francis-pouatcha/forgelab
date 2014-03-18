package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.repo.DeliveryRepository;

public class DeliveryMerger
{

   @Inject
   private DeliveryRepository repository;

   public Delivery bindComposed(Delivery entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Delivery bindAggregated(Delivery entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Delivery> entities)
   {
      if (entities == null)
         return;
      HashSet<Delivery> oldCol = new HashSet<Delivery>(entities);
      entities.clear();
      for (Delivery entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Delivery> entities)
   {
      if (entities == null)
         return;
      HashSet<Delivery> oldCol = new HashSet<Delivery>(entities);
      entities.clear();
      for (Delivery entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Delivery unbind(final Delivery entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Delivery newEntity = new Delivery();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Delivery> unbind(final Set<Delivery> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Delivery>();
      //       HashSet<Delivery> cache = new HashSet<Delivery>(entities);
      //       entities.clear();
      //       for (Delivery entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
