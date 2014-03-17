package org.adorsys.adpharma.client.jpa.inventory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryLoadService extends Service<Inventory>
{

   @Inject
   private InventoryService remoteService;

   private Long id;

   public InventoryLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
