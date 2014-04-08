package org.adorsys.adpharma.client.jpa.cashout;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerCashier;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class CashOutCashierListCell extends AbstractToStringListCell<CashOutCashier>
{

	   @Override
	   protected String getToString(CashOutCashier item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "loginName", "gender");
	   }

}
