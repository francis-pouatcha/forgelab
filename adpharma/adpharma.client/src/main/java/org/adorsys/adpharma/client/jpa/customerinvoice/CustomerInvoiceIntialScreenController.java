package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CustomerInvoiceIntialScreenController extends InitialScreenController<CustomerInvoice>
{
   @Override
   public CustomerInvoice newEntity()
   {
      return new CustomerInvoice();
   }
}
