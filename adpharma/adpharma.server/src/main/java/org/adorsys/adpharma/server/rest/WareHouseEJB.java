package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.WareHouse;
import org.adorsys.adpharma.server.repo.WareHouseRepository;

@Stateless
public class WareHouseEJB
{

   @Inject
   private WareHouseRepository repository;

   @Inject
   private AgencyMerger agencyMerger;

   public WareHouse create(WareHouse entity)
   {
      return repository.save(attach(entity));
   }

   public WareHouse deleteById(Long id)
   {
      WareHouse entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public WareHouse update(WareHouse entity)
   {
      return repository.save(attach(entity));
   }

   public WareHouse findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<WareHouse> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<WareHouse> findBy(WareHouse entity, int start, int max, SingularAttribute<WareHouse, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(WareHouse entity, SingularAttribute<WareHouse, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<WareHouse> findByLike(WareHouse entity, int start, int max, SingularAttribute<WareHouse, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(WareHouse entity, SingularAttribute<WareHouse, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private WareHouse attach(WareHouse entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
}
