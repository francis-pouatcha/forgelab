package org.adorsys.adpharma.client.jpa.disbursement;

import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DisbursementCashDrawerListCell extends AbstractToStringListCell<DisbursementCashDrawer>
{

	   @Override
	   protected String getToString(DisbursementCashDrawer item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "cashDrawerNumber");
	   }

}
