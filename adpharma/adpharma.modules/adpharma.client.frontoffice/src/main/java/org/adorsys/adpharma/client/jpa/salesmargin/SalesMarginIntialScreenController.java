package org.adorsys.adpharma.client.jpa.salesmargin;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SalesMarginIntialScreenController extends InitialScreenController<SalesMargin>
{
   @Override
   public SalesMargin newEntity()
   {
      return new SalesMargin();
   }
}
