package org.adorsys.adpharma.client.jpa.cashout;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CashOutIntialScreenController extends InitialScreenController<CashOut>
{
	   @Override
	   public CashOut newEntity()
	   {
	      return new CashOut();
	   }

}
