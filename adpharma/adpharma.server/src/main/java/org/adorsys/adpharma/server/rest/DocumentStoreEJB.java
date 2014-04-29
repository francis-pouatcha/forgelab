package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.DocumentStore;
import org.adorsys.adpharma.server.repo.DocumentStoreRepository;

@Stateless
public class DocumentStoreEJB
{

   @Inject
   private DocumentStoreRepository repository;

   @Inject
   private LoginMerger loginMerger;

   public DocumentStore create(DocumentStore entity)
   {
      return repository.save(attach(entity));
   }

   public DocumentStore deleteById(Long id)
   {
      DocumentStore entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public DocumentStore update(DocumentStore entity)
   {
      return repository.save(attach(entity));
   }

   public DocumentStore findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<DocumentStore> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<DocumentStore> findBy(DocumentStore entity, int start, int max, SingularAttribute<DocumentStore, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DocumentStore entity, SingularAttribute<DocumentStore, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DocumentStore> findByLike(DocumentStore entity, int start, int max, SingularAttribute<DocumentStore, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DocumentStore entity, SingularAttribute<DocumentStore, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DocumentStore attach(DocumentStore entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

      return entity;
   }
}
