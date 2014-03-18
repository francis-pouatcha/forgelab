package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.stockmovement.StockMovement;

@Singleton
public class StockMovementCreateService extends Service<StockMovement>
{

   private StockMovement model;

   @Inject
   private StockMovementService remoteService;

   public StockMovementCreateService setModel(StockMovement model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
