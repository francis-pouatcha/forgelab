package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public class SupplierInvoiceRemoveService extends Service<SupplierInvoice>
{

   @Inject
   private SupplierInvoiceService remoteService;

   private SupplierInvoice entity;

   public SupplierInvoiceRemoveService setEntity(SupplierInvoice entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
