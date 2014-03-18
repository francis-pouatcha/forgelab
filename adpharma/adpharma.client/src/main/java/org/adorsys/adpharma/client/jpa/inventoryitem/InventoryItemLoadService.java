package org.adorsys.adpharma.client.jpa.inventoryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryItemLoadService extends Service<InventoryItem>
{

   @Inject
   private InventoryItemService remoteService;

   private Long id;

   public InventoryItemLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
