package org.adorsys.adpharma.client.jpa.customercategory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCategoryEditService extends Service<CustomerCategory>
{

   @Inject
   private CustomerCategoryService remoteService;

   private CustomerCategory entity;

   public CustomerCategoryEditService setCustomerCategory(CustomerCategory entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
