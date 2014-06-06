package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceLoadService extends Service<CustomerInvoice>
{

   @Inject
   private CustomerInvoiceService remoteService;

   private Long id;

   public CustomerInvoiceLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
