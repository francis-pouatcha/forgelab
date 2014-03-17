package org.adorsys.adpharma.client.jpa.customer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerEditService extends Service<Customer>
{

   @Inject
   private CustomerService remoteService;

   private Customer entity;

   public CustomerEditService setCustomer(Customer entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
