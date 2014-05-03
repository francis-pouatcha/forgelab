package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SupplierInvoiceCreateService extends Service<SupplierInvoice>
{

   private SupplierInvoice model;

   @Inject
   private SupplierInvoiceService remoteService;

   public SupplierInvoiceCreateService setModel(SupplierInvoice model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<SupplierInvoice> createTask()
   {
      return new Task<SupplierInvoice>()
      {
         @Override
         protected SupplierInvoice call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
