package org.adorsys.adpharma.client.jpa.stockmovement;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class StockMovementIntialScreenController extends InitialScreenController<StockMovement>
{
   @Override
   public StockMovement newEntity()
   {
      return new StockMovement();
   }
}
