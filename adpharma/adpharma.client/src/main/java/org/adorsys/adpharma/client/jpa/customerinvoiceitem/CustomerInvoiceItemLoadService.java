package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceItemLoadService extends Service<CustomerInvoiceItem>
{

   @Inject
   private CustomerInvoiceItemService remoteService;

   private Long id;

   public CustomerInvoiceItemLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<CustomerInvoiceItem> createTask()
   {
      return new Task<CustomerInvoiceItem>()
      {
         @Override
         protected CustomerInvoiceItem call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
