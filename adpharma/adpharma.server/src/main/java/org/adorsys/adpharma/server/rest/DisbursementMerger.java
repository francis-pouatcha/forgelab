package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.Disbursement;
import org.adorsys.adpharma.server.repo.DisbursementRepository;

public class DisbursementMerger
{

   @Inject
   private DisbursementRepository repository;

   public Disbursement bindComposed(Disbursement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Disbursement bindAggregated(Disbursement entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Disbursement> entities)
   {
      if (entities == null)
         return;
      HashSet<Disbursement> oldCol = new HashSet<Disbursement>(entities);
      entities.clear();
      for (Disbursement entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Disbursement> entities)
   {
      if (entities == null)
         return;
      HashSet<Disbursement> oldCol = new HashSet<Disbursement>(entities);
      entities.clear();
      for (Disbursement entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Disbursement unbind(final Disbursement entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Disbursement newEntity = new Disbursement();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Disbursement> unbind(final Set<Disbursement> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Disbursement>();
      //       HashSet<Disbursement> cache = new HashSet<Disbursement>(entities);
      //       entities.clear();
      //       for (Disbursement entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
