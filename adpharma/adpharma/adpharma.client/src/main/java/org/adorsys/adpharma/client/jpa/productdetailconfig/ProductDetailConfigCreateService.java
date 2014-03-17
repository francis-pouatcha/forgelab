package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;

@Singleton
public class ProductDetailConfigCreateService extends Service<ProductDetailConfig>
{

   private ProductDetailConfig model;

   @Inject
   private ProductDetailConfigService remoteService;

   public ProductDetailConfigCreateService setModel(ProductDetailConfig model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
