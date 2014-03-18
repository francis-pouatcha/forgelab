package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.repo.CashDrawerRepository;

public class CashDrawerMerger
{

   @Inject
   private CashDrawerRepository repository;

   public CashDrawer bindComposed(CashDrawer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CashDrawer bindAggregated(CashDrawer entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CashDrawer> entities)
   {
      if (entities == null)
         return;
      HashSet<CashDrawer> oldCol = new HashSet<CashDrawer>(entities);
      entities.clear();
      for (CashDrawer entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CashDrawer> entities)
   {
      if (entities == null)
         return;
      HashSet<CashDrawer> oldCol = new HashSet<CashDrawer>(entities);
      entities.clear();
      for (CashDrawer entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CashDrawer unbind(final CashDrawer entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CashDrawer newEntity = new CashDrawer();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CashDrawer> unbind(final Set<CashDrawer> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CashDrawer>();
      //       HashSet<CashDrawer> cache = new HashSet<CashDrawer>(entities);
      //       entities.clear();
      //       for (CashDrawer entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
