package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceEditService extends Service<CustomerInvoice>
{

   @Inject
   private CustomerInvoiceService remoteService;

   private CustomerInvoice entity;

   public CustomerInvoiceEditService setCustomerInvoice(CustomerInvoice entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<CustomerInvoice> createTask()
   {
      return new Task<CustomerInvoice>()
      {
         @Override
         protected CustomerInvoice call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
