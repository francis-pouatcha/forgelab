package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.Section;
import org.adorsys.adpharma.server.repo.SectionRepository;

@Stateless
public class SectionEJB
{

   @Inject
   private SectionRepository repository;

   @Inject
   private AgencyMerger agencyMerger;

   public Section create(Section entity)
   {
      return repository.save(attach(entity));
   }

   public Section deleteById(Long id)
   {
      Section entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Section update(Section entity)
   {
      return repository.save(attach(entity));
   }

   public Section findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Section> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Section> findBy(Section entity, int start, int max, SingularAttribute<Section, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Section entity, SingularAttribute<Section, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Section> findByLike(Section entity, int start, int max, SingularAttribute<Section, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Section entity, SingularAttribute<Section, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Section attach(Section entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
