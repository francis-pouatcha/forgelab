package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class PaymentCashierListCell extends AbstractToStringListCell<Login>
{

   @Override
   protected String getToString(Login item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName");
   }

}
