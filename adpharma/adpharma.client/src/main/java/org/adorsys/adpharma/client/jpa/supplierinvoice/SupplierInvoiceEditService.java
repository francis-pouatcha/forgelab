package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierInvoiceEditService extends Service<SupplierInvoice>
{

   @Inject
   private SupplierInvoiceService remoteService;

   private SupplierInvoice entity;

   public SupplierInvoiceEditService setSupplierInvoice(SupplierInvoice entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
