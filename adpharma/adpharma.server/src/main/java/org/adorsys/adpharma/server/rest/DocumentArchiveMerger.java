package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.DocumentArchive;
import org.adorsys.adpharma.server.repo.DocumentArchiveRepository;

public class DocumentArchiveMerger
{

   @Inject
   private DocumentArchiveRepository repository;

   public DocumentArchive bindComposed(DocumentArchive entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public DocumentArchive bindAggregated(DocumentArchive entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<DocumentArchive> entities)
   {
      if (entities == null)
         return;
      HashSet<DocumentArchive> oldCol = new HashSet<DocumentArchive>(entities);
      entities.clear();
      for (DocumentArchive entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<DocumentArchive> entities)
   {
      if (entities == null)
         return;
      HashSet<DocumentArchive> oldCol = new HashSet<DocumentArchive>(entities);
      entities.clear();
      for (DocumentArchive entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public DocumentArchive unbind(final DocumentArchive entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      DocumentArchive newEntity = new DocumentArchive();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<DocumentArchive> unbind(final Set<DocumentArchive> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<DocumentArchive>();
      //       HashSet<DocumentArchive> cache = new HashSet<DocumentArchive>(entities);
      //       entities.clear();
      //       for (DocumentArchive entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
