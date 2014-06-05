package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceItemRemoveService extends Service<CustomerInvoiceItem>
{

   @Inject
   private CustomerInvoiceItemService remoteService;

   private CustomerInvoiceItem entity;

   public CustomerInvoiceItemRemoveService setEntity(CustomerInvoiceItem entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
