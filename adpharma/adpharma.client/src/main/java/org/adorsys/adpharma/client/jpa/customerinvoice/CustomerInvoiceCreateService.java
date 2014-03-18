package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

@Singleton
public class CustomerInvoiceCreateService extends Service<CustomerInvoice>
{

   private CustomerInvoice model;

   @Inject
   private CustomerInvoiceService remoteService;

   public CustomerInvoiceCreateService setModel(CustomerInvoice model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
