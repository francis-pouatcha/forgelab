package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierInvoiceItemLoadService extends Service<SupplierInvoiceItem>
{

   @Inject
   private SupplierInvoiceItemService remoteService;

   private Long id;

   public SupplierInvoiceItemLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
