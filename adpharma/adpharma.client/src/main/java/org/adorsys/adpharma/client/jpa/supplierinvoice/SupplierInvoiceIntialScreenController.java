package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SupplierInvoiceIntialScreenController extends InitialScreenController<SupplierInvoice>
{
   @Override
   public SupplierInvoice newEntity()
   {
      return new SupplierInvoice();
   }
}
