package org.adorsys.adpharma.client.jpa.inventoryitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class InventoryItemIntialScreenController extends InitialScreenController<InventoryItem>
{
   @Override
   public InventoryItem newEntity()
   {
      return new InventoryItem();
   }
}
