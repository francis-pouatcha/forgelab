package org.adorsys.adpharma.client.jpa.inventory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryRemoveService extends Service<Inventory>
{

   @Inject
   private InventoryService remoteService;

   private Inventory entity;

   public InventoryRemoveService setEntity(Inventory entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
