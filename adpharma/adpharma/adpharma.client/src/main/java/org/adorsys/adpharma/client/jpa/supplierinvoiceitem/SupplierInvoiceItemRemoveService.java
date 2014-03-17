package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;

public class SupplierInvoiceItemRemoveService extends Service<SupplierInvoiceItem>
{

   @Inject
   private SupplierInvoiceItemService remoteService;

   private SupplierInvoiceItem entity;

   public SupplierInvoiceItemRemoveService setEntity(SupplierInvoiceItem entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
