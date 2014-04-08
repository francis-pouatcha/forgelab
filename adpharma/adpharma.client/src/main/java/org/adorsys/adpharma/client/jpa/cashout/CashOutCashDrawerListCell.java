package org.adorsys.adpharma.client.jpa.cashout;

import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class CashOutCashDrawerListCell extends AbstractToStringListCell<CashOutCashDrawer>
{

	   @Override
	   protected String getToString(CashOutCashDrawer item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "cashDrawerNumber");
	   }

}
