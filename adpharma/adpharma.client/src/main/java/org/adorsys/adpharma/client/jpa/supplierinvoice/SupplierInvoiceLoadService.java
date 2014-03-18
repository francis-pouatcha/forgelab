package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierInvoiceLoadService extends Service<SupplierInvoice>
{

   @Inject
   private SupplierInvoiceService remoteService;

   private Long id;

   public SupplierInvoiceLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
