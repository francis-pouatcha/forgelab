package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;

public class SupplierInvoiceItemEditService extends Service<SupplierInvoiceItem>
{

   @Inject
   private SupplierInvoiceItemService remoteService;

   private SupplierInvoiceItem entity;

   public SupplierInvoiceItemEditService setSupplierInvoiceItem(SupplierInvoiceItem entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
