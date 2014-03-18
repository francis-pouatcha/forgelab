package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;

public class ProductFamilyEditService extends Service<ProductFamily>
{

   @Inject
   private ProductFamilyService remoteService;

   private ProductFamily entity;

   public ProductFamilyEditService setProductFamily(ProductFamily entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
