package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductDetailConfigLoadService extends Service<ProductDetailConfig>
{

   @Inject
   private ProductDetailConfigService remoteService;

   private Long id;

   public ProductDetailConfigLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
