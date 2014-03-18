package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerVoucherCustomerInvoiceListCell extends AbstractToStringListCell<CustomerInvoice>
{

   @Override
   protected String getToString(CustomerInvoice item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "invoiceNumber");
   }

}
