package org.adorsys.adpharma.client.jpa.inventory;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class InventoryIntialScreenController extends InitialScreenController<Inventory>
{
   @Override
   public Inventory newEntity()
   {
      return new Inventory();
   }
}
