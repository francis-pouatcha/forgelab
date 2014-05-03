package org.adorsys.adpharma.client.jpa.inventoryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryItemRemoveService extends Service<InventoryItem>
{

   @Inject
   private InventoryItemService remoteService;

   private InventoryItem entity;

   public InventoryItemRemoveService setEntity(InventoryItem entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<InventoryItem> createTask()
   {
      return new Task<InventoryItem>()
      {
         @Override
         protected InventoryItem call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
