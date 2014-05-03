package org.adorsys.adpharma.client.jpa.inventoryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InventoryItemCreateService extends Service<InventoryItem>
{

   private InventoryItem model;

   @Inject
   private InventoryItemService remoteService;

   public InventoryItemCreateService setModel(InventoryItem model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
