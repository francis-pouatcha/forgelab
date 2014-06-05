package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductFamilyRemoveService extends Service<ProductFamily>
{

   @Inject
   private ProductFamilyService remoteService;

   private ProductFamily entity;

   public ProductFamilyRemoveService setEntity(ProductFamily entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<ProductFamily> createTask()
   {
      return new Task<ProductFamily>()
      {
         @Override
         protected ProductFamily call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
