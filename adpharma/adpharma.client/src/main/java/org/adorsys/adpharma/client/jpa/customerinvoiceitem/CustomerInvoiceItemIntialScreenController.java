package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CustomerInvoiceItemIntialScreenController extends InitialScreenController<CustomerInvoiceItem>
{
   @Override
   public CustomerInvoiceItem newEntity()
   {
      return new CustomerInvoiceItem();
   }
}
