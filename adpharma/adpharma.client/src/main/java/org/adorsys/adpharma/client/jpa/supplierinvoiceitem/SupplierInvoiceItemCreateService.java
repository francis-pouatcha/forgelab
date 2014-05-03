package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SupplierInvoiceItemCreateService extends Service<SupplierInvoiceItem>
{

   private SupplierInvoiceItem model;

   @Inject
   private SupplierInvoiceItemService remoteService;

   public SupplierInvoiceItemCreateService setModel(SupplierInvoiceItem model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<SupplierInvoiceItem> createTask()
   {
      return new Task<SupplierInvoiceItem>()
      {
         @Override
         protected SupplierInvoiceItem call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
