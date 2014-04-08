package org.adorsys.adpharma.client.jpa.cashout;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class CashOutAgencyListCell extends AbstractToStringListCell<CashOutAgency>
{

	   @Override
	   protected String getToString(CashOutAgency item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "agencyNumber", "name");
	   }
}
