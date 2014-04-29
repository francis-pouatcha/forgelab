package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.DocumentArchive;
import org.adorsys.adpharma.server.repo.DocumentArchiveRepository;

@Stateless
public class DocumentArchiveEJB
{

   @Inject
   private DocumentArchiveRepository repository;

   @Inject
   private LoginMerger loginMerger;

   public DocumentArchive create(DocumentArchive entity)
   {
      return repository.save(attach(entity));
   }

   public DocumentArchive deleteById(Long id)
   {
      DocumentArchive entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public DocumentArchive update(DocumentArchive entity)
   {
      return repository.save(attach(entity));
   }

   public DocumentArchive findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<DocumentArchive> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<DocumentArchive> findBy(DocumentArchive entity, int start, int max, SingularAttribute<DocumentArchive, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DocumentArchive entity, SingularAttribute<DocumentArchive, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DocumentArchive> findByLike(DocumentArchive entity, int start, int max, SingularAttribute<DocumentArchive, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DocumentArchive entity, SingularAttribute<DocumentArchive, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DocumentArchive attach(DocumentArchive entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

      return entity;
   }
}
