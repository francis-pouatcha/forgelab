package org.adorsys.adpharma.client.jpa.disbursement;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DisbursementIntialScreenController extends InitialScreenController<Disbursement>
{
	   @Override
	   public Disbursement newEntity()
	   {
	      return new Disbursement();
	   }

}
