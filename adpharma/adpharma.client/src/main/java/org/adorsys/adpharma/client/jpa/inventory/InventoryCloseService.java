package org.adorsys.adpharma.client.jpa.inventory;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryCloseService  extends Service<Inventory>
{

	   private Inventory model;

	   @Inject
	   private InventoryService remoteService;

	   public InventoryCloseService setModel(Inventory model)
	   {
	      this.model = model;
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
	            if (model == null)
	               return null;
	            return remoteService.closeInventory(model);
	         }
	      };
	   }

}
