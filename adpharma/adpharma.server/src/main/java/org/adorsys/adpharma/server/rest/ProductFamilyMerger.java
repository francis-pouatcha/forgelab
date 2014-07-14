package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.server.jpa.ProductFamily;
import org.adorsys.adpharma.server.repo.ProductFamilyRepository;

public class ProductFamilyMerger
{

   @Inject
   private ProductFamilyRepository repository;

   public ProductFamily bindComposed(ProductFamily entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ProductFamily bindAggregated(ProductFamily entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ProductFamily> entities)
   {
      if (entities == null)
         return;
      HashSet<ProductFamily> oldCol = new HashSet<ProductFamily>(entities);
      entities.clear();
      for (ProductFamily entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ProductFamily> entities)
   {
      if (entities == null)
         return;
      HashSet<ProductFamily> oldCol = new HashSet<ProductFamily>(entities);
      entities.clear();
      for (ProductFamily entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ProductFamily unbind(final ProductFamily entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ProductFamily newEntity = new ProductFamily();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ProductFamily> unbind(final Set<ProductFamily> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ProductFamily>();
      //       HashSet<ProductFamily> cache = new HashSet<ProductFamily>(entities);
      //       entities.clear();
      //       for (ProductFamily entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
