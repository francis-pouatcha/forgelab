package org.adorsys.adpharma.client.jpa.customercategory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerCategoryCreateService extends Service<CustomerCategory>
{

   private CustomerCategory model;

   @Inject
   private CustomerCategoryService remoteService;

   public CustomerCategoryCreateService setModel(CustomerCategory model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<CustomerCategory> createTask()
   {
      return new Task<CustomerCategory>()
      {
         @Override
         protected CustomerCategory call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
