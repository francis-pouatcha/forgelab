package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class CustomerInvoiceSalesOrderListCell extends AbstractToStringListCell<CustomerInvoiceSalesOrder>
{

   @Override
   protected String getToString(CustomerInvoiceSalesOrder item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "soNumber");
   }

}
