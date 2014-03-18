package org.adorsys.adpharma.server.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adpharma.server.jpa.ProductDetailConfig;
import org.adorsys.adpharma.server.repo.ProductDetailConfigRepository;

public class ProductDetailConfigMerger
{

   @Inject
   private ProductDetailConfigRepository repository;

   public ProductDetailConfig bindComposed(ProductDetailConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ProductDetailConfig bindAggregated(ProductDetailConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ProductDetailConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<ProductDetailConfig> oldCol = new HashSet<ProductDetailConfig>(entities);
      entities.clear();
      for (ProductDetailConfig entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ProductDetailConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<ProductDetailConfig> oldCol = new HashSet<ProductDetailConfig>(entities);
      entities.clear();
      for (ProductDetailConfig entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ProductDetailConfig unbind(final ProductDetailConfig entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ProductDetailConfig newEntity = new ProductDetailConfig();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ProductDetailConfig> unbind(final Set<ProductDetailConfig> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ProductDetailConfig>();
      //       HashSet<ProductDetailConfig> cache = new HashSet<ProductDetailConfig>(entities);
      //       entities.clear();
      //       for (ProductDetailConfig entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
