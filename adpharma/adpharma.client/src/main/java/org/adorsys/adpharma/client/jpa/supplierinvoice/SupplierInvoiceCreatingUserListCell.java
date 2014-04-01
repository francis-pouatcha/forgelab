package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class SupplierInvoiceCreatingUserListCell extends AbstractToStringListCell<SupplierInvoiceCreatingUser>
{

   @Override
   protected String getToString(SupplierInvoiceCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
