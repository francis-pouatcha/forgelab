package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;
import org.adorsys.adpharma.server.repo.WareHouseArticleLotRepository;

public class WareHouseArticleLotMerger
{

   @Inject
   private WareHouseArticleLotRepository repository;

   public WareHouseArticleLot bindComposed(WareHouseArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public WareHouseArticleLot bindAggregated(WareHouseArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<WareHouseArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<WareHouseArticleLot> oldCol = new HashSet<WareHouseArticleLot>(entities);
      entities.clear();
      for (WareHouseArticleLot entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<WareHouseArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<WareHouseArticleLot> oldCol = new HashSet<WareHouseArticleLot>(entities);
      entities.clear();
      for (WareHouseArticleLot entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public WareHouseArticleLot unbind(final WareHouseArticleLot entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      WareHouseArticleLot newEntity = new WareHouseArticleLot();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<WareHouseArticleLot> unbind(final Set<WareHouseArticleLot> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<WareHouseArticleLot>();
      //       HashSet<WareHouseArticleLot> cache = new HashSet<WareHouseArticleLot>(entities);
      //       entities.clear();
      //       for (WareHouseArticleLot entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
