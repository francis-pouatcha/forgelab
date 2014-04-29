package org.adorsys.adpharma.client.jpa.disbursement;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerCashier;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DisbursementCashierListCell extends AbstractToStringListCell<DisbursementCashier>
{

	   @Override
	   protected String getToString(DisbursementCashier item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "loginName", "gender");
	   }

}
