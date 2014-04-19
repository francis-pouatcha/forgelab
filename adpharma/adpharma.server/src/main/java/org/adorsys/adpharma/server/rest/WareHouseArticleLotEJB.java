package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;
import org.adorsys.adpharma.server.repo.WareHouseArticleLotRepository;

@Stateless
public class WareHouseArticleLotEJB
{

   @Inject
   private WareHouseArticleLotRepository repository;

   @Inject
   private WareHouseMerger wareHouseMerger;

   @Inject
   private ArticleLotMerger articleLotMerger;

   public WareHouseArticleLot create(WareHouseArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public WareHouseArticleLot deleteById(Long id)
   {
      WareHouseArticleLot entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public WareHouseArticleLot update(WareHouseArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public WareHouseArticleLot findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<WareHouseArticleLot> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<WareHouseArticleLot> findBy(WareHouseArticleLot entity, int start, int max, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(WareHouseArticleLot entity, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<WareHouseArticleLot> findByLike(WareHouseArticleLot entity, int start, int max, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(WareHouseArticleLot entity, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private WareHouseArticleLot attach(WareHouseArticleLot entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setWareHouse(wareHouseMerger.bindAggregated(entity.getWareHouse()));

      // aggregated
      entity.setArticleLot(articleLotMerger.bindAggregated(entity.getArticleLot()));

      return entity;
   }
}
