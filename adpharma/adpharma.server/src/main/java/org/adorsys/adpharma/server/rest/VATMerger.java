package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.VATRepository;

public class VATMerger
{

   @Inject
   private VATRepository repository;

   public VAT bindComposed(VAT entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public VAT bindAggregated(VAT entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<VAT> entities)
   {
      if (entities == null)
         return;
      HashSet<VAT> oldCol = new HashSet<VAT>(entities);
      entities.clear();
      for (VAT entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<VAT> entities)
   {
      if (entities == null)
         return;
      HashSet<VAT> oldCol = new HashSet<VAT>(entities);
      entities.clear();
      for (VAT entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public VAT unbind(final VAT entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      VAT newEntity = new VAT();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<VAT> unbind(final Set<VAT> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<VAT>();
      //       HashSet<VAT> cache = new HashSet<VAT>(entities);
      //       entities.clear();
      //       for (VAT entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
