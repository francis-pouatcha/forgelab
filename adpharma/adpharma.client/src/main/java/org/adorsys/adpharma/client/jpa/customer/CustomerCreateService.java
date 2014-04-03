package org.adorsys.adpharma.client.jpa.customer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerCreateService extends Service<Customer>
{

   private Customer model;

   @Inject
   private CustomerService remoteService;

   public CustomerCreateService setModel(Customer model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
