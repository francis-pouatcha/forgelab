package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;

@Singleton
public class CustomerInvoiceItemCreateService extends Service<CustomerInvoiceItem>
{

   private CustomerInvoiceItem model;

   @Inject
   private CustomerInvoiceItemService remoteService;

   public CustomerInvoiceItemCreateService setModel(CustomerInvoiceItem model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
