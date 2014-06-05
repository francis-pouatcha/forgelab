package org.adorsys.adpharma.client.jpa.cashdrawer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class CashDrawerCashierListCell extends AbstractToStringListCell<CashDrawerCashier>
{

   @Override
   protected String getToString(CashDrawerCashier item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
