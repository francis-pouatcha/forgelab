package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.repo.DebtStatementRepository;

public class DebtStatementMerger
{

   @Inject
   private DebtStatementRepository repository;

   public DebtStatement bindComposed(DebtStatement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public DebtStatement bindAggregated(DebtStatement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<DebtStatement> entities)
   {
      if (entities == null)
         return;
      HashSet<DebtStatement> oldCol = new HashSet<DebtStatement>(entities);
      entities.clear();
      for (DebtStatement entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<DebtStatement> entities)
   {
      if (entities == null)
         return;
      HashSet<DebtStatement> oldCol = new HashSet<DebtStatement>(entities);
      entities.clear();
      for (DebtStatement entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public DebtStatement unbind(final DebtStatement entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      DebtStatement newEntity = new DebtStatement();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      newEntity.setInsurrance(entity.getInsurrance());
      return newEntity;
   }

   public Set<DebtStatement> unbind(final Set<DebtStatement> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<DebtStatement>();
      //       HashSet<DebtStatement> cache = new HashSet<DebtStatement>(entities);
      //       entities.clear();
      //       for (DebtStatement entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
