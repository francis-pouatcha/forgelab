package org.adorsys.adpharma.client.jpa.customercategory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerCategoryLoadService extends Service<CustomerCategory>
{

   @Inject
   private CustomerCategoryService remoteService;

   private Long id;

   public CustomerCategoryLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
