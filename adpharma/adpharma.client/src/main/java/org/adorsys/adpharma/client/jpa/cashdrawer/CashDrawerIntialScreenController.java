package org.adorsys.adpharma.client.jpa.cashdrawer;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CashDrawerIntialScreenController extends InitialScreenController<CashDrawer>
{
   @Override
   public CashDrawer newEntity()
   {
      return new CashDrawer();
   }
}
