package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class StockMovementRemoveService extends Service<StockMovement>
{

   @Inject
   private StockMovementService remoteService;

   private StockMovement entity;

   public StockMovementRemoveService setEntity(StockMovement entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<StockMovement> createTask()
   {
      return new Task<StockMovement>()
      {
         @Override
         protected StockMovement call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
