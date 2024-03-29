package org.adorsys.adpharma.client.jpa.cashdrawer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class CashDrawerClosedByListCell extends AbstractToStringListCell<CashDrawerClosedBy>
{

   @Override
   protected String getToString(CashDrawerClosedBy item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
