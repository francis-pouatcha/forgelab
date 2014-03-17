package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.PrescriptionBook;
import org.adorsys.adpharma.server.repo.PrescriptionBookRepository;

public class PrescriptionBookMerger
{

   @Inject
   private PrescriptionBookRepository repository;

   public PrescriptionBook bindComposed(PrescriptionBook entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PrescriptionBook bindAggregated(PrescriptionBook entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PrescriptionBook> entities)
   {
      if (entities == null)
         return;
      HashSet<PrescriptionBook> oldCol = new HashSet<PrescriptionBook>(entities);
      entities.clear();
      for (PrescriptionBook entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PrescriptionBook> entities)
   {
      if (entities == null)
         return;
      HashSet<PrescriptionBook> oldCol = new HashSet<PrescriptionBook>(entities);
      entities.clear();
      for (PrescriptionBook entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PrescriptionBook unbind(final PrescriptionBook entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PrescriptionBook newEntity = new PrescriptionBook();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PrescriptionBook> unbind(final Set<PrescriptionBook> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PrescriptionBook>();
      //       HashSet<PrescriptionBook> cache = new HashSet<PrescriptionBook>(entities);
      //       entities.clear();
      //       for (PrescriptionBook entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
