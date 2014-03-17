package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;

public class CustomerInvoiceItemEditService extends Service<CustomerInvoiceItem>
{

   @Inject
   private CustomerInvoiceItemService remoteService;

   private CustomerInvoiceItem entity;

   public CustomerInvoiceItemEditService setCustomerInvoiceItem(CustomerInvoiceItem entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
