package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.repo.AgencyRepository;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

@Stateless
public class AgencyEJB
{

   @Inject
   private AgencyRepository repository;

   @Inject
   private CompanyMerger companyMerger;

   public Agency create(Agency entity)
   {
	   Agency save = repository.save(attach(entity));
//		save.setAgencyNumber(SequenceGenerator.getSequence(SequenceGenerator.AGENCY_SEQUENCE_PREFIXE));
      return repository.save(save);
   }

   public Agency deleteById(Long id)
   {
      Agency entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Agency update(Agency entity)
   {
      return repository.save(attach(entity));
   }

   public Agency findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Agency> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Agency> findBy(Agency entity, int start, int max, SingularAttribute<Agency, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Agency entity, SingularAttribute<Agency, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Agency> findByLike(Agency entity, int start, int max, SingularAttribute<Agency, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Agency entity, SingularAttribute<Agency, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Agency attach(Agency entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCompany(companyMerger.bindAggregated(entity.getCompany()));

      return entity;
   }
}
