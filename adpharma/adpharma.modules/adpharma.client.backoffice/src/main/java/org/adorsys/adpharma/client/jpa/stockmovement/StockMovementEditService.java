package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class StockMovementEditService extends Service<StockMovement>
{

   @Inject
   private StockMovementService remoteService;

   private StockMovement entity;

   public StockMovementEditService setStockMovement(StockMovement entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
