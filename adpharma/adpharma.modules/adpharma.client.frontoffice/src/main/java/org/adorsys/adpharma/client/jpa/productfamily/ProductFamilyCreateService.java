package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProductFamilyCreateService extends Service<ProductFamily>
{

   private ProductFamily model;

   @Inject
   private ProductFamilyService remoteService;

   public ProductFamilyCreateService setModel(ProductFamily model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
