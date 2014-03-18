package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductFamilyLoadService extends Service<ProductFamily>
{

   @Inject
   private ProductFamilyService remoteService;

   private Long id;

   public ProductFamilyLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
