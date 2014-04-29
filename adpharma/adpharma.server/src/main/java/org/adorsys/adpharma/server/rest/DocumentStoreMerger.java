package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.DocumentStore;
import org.adorsys.adpharma.server.repo.DocumentStoreRepository;

public class DocumentStoreMerger
{

   @Inject
   private DocumentStoreRepository repository;

   public DocumentStore bindComposed(DocumentStore entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public DocumentStore bindAggregated(DocumentStore entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<DocumentStore> entities)
   {
      if (entities == null)
         return;
      HashSet<DocumentStore> oldCol = new HashSet<DocumentStore>(entities);
      entities.clear();
      for (DocumentStore entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<DocumentStore> entities)
   {
      if (entities == null)
         return;
      HashSet<DocumentStore> oldCol = new HashSet<DocumentStore>(entities);
      entities.clear();
      for (DocumentStore entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public DocumentStore unbind(final DocumentStore entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      DocumentStore newEntity = new DocumentStore();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<DocumentStore> unbind(final Set<DocumentStore> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<DocumentStore>();
      //       HashSet<DocumentStore> cache = new HashSet<DocumentStore>(entities);
      //       entities.clear();
      //       for (DocumentStore entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
