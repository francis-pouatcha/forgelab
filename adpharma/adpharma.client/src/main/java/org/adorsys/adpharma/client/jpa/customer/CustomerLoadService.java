package org.adorsys.adpharma.client.jpa.customer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerLoadService extends Service<Customer>
{

   @Inject
   private CustomerService remoteService;

   private Long id;

   public CustomerLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Customer> createTask()
   {
      return new Task<Customer>()
      {
         @Override
         protected Customer call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
