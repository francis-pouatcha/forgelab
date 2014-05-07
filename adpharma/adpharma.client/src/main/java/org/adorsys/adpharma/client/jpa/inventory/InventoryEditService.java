package org.adorsys.adpharma.client.jpa.inventory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryEditService extends Service<Inventory>
{

   @Inject
   private InventoryService remoteService;

   private Inventory entity;

   public InventoryEditService setInventory(Inventory entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<Inventory> createTask()
   {
      return new Task<Inventory>()
      {
         @Override
         protected Inventory call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
