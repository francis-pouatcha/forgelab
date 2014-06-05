package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class PaymentCashDrawerListCell extends AbstractToStringListCell<PaymentCashDrawer>
{

   @Override
   protected String getToString(PaymentCashDrawer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "cashDrawerNumber");
   }

}
