package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;

public class ProductDetailConfigEditService extends Service<ProductDetailConfig>
{

   @Inject
   private ProductDetailConfigService remoteService;

   private ProductDetailConfig entity;

   public ProductDetailConfigEditService setProductDetailConfig(ProductDetailConfig entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<ProductDetailConfig> createTask()
   {
      return new Task<ProductDetailConfig>()
      {
         @Override
         protected ProductDetailConfig call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
