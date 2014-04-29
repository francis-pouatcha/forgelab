package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.WareHouse;
import org.adorsys.adpharma.server.repo.WareHouseRepository;

public class WareHouseMerger
{

   @Inject
   private WareHouseRepository repository;

   public WareHouse bindComposed(WareHouse entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public WareHouse bindAggregated(WareHouse entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<WareHouse> entities)
   {
      if (entities == null)
         return;
      HashSet<WareHouse> oldCol = new HashSet<WareHouse>(entities);
      entities.clear();
      for (WareHouse entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<WareHouse> entities)
   {
      if (entities == null)
         return;
      HashSet<WareHouse> oldCol = new HashSet<WareHouse>(entities);
      entities.clear();
      for (WareHouse entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public WareHouse unbind(final WareHouse entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      WareHouse newEntity = new WareHouse();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<WareHouse> unbind(final Set<WareHouse> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<WareHouse>();
      //       HashSet<WareHouse> cache = new HashSet<WareHouse>(entities);
      //       entities.clear();
      //       for (WareHouse entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
