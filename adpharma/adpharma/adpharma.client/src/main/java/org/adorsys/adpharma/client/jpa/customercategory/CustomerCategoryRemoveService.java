package org.adorsys.adpharma.client.jpa.customercategory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCategoryRemoveService extends Service<CustomerCategory>
{

   @Inject
   private CustomerCategoryService remoteService;

   private CustomerCategory entity;

   public CustomerCategoryRemoveService setEntity(CustomerCategory entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
