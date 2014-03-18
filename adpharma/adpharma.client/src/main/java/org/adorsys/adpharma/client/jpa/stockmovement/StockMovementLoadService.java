package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class StockMovementLoadService extends Service<StockMovement>
{

   @Inject
   private StockMovementService remoteService;

   private Long id;

   public StockMovementLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
