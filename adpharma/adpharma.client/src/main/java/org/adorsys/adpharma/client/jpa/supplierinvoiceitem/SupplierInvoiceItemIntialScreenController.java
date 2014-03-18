package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SupplierInvoiceItemIntialScreenController extends InitialScreenController<SupplierInvoiceItem>
{
   @Override
   public SupplierInvoiceItem newEntity()
   {
      return new SupplierInvoiceItem();
   }
}
