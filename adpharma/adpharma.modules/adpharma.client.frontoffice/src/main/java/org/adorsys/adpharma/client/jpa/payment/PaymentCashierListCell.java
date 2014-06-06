package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class PaymentCashierListCell extends AbstractToStringListCell<PaymentCashier>
{

   @Override
   protected String getToString(PaymentCashier item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
