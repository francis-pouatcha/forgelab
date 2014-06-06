package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class CustomerInvoiceCreatingUserListCell extends AbstractToStringListCell<CustomerInvoiceCreatingUser>
{

   @Override
   protected String getToString(CustomerInvoiceCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
