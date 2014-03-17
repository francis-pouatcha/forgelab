package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerInvoiceRemoveService extends Service<CustomerInvoice>
{

   @Inject
   private CustomerInvoiceService remoteService;

   private CustomerInvoice entity;

   public CustomerInvoiceRemoveService setEntity(CustomerInvoice entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
