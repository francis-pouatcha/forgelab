package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adpharma.server.jpa.ProductFamily;
import org.adorsys.adpharma.server.repo.ProductFamilyRepository;

@Stateless
public class ProductFamilyEJB
{

   @Inject
   private ProductFamilyRepository repository;

   @Inject
   private ProductFamilyMerger productFamilyMerger;

   public ProductFamily create(ProductFamily entity)
   {
      return repository.save(attach(entity));
   }

   public ProductFamily deleteById(Long id)
   {
      ProductFamily entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public ProductFamily update(ProductFamily entity)
   {
      return repository.save(attach(entity));
   }

   public ProductFamily findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<ProductFamily> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<ProductFamily> findBy(ProductFamily entity, int start, int max, SingularAttribute<ProductFamily, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(ProductFamily entity, SingularAttribute<ProductFamily, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<ProductFamily> findByLike(ProductFamily entity, int start, int max, SingularAttribute<ProductFamily, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(ProductFamily entity, SingularAttribute<ProductFamily, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private ProductFamily attach(ProductFamily entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setParentFamily(productFamilyMerger.bindAggregated(entity.getParentFamily()));

      return entity;
   }
}
