package org.adorsys.adpharma.client.jpa.disbursement;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DisbursementAgencyListCell extends AbstractToStringListCell<DisbursementAgency>
{

	   @Override
	   protected String getToString(DisbursementAgency item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "agencyNumber", "name");
	   }
}
