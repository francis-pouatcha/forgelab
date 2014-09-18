package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;

public class PeriodicalMovementService  extends Service<StockMovementSearchResult>
{

	   @Inject
	   private StockMovementService serviceService;

	   private PeriodicalDataSearchInput data;

	   public PeriodicalMovementService setData(PeriodicalDataSearchInput data)
	   {
	      this.data = data;
	      return this;
	   }

	   @Override
	   protected Task<StockMovementSearchResult> createTask()
	   {
	      return new Task<StockMovementSearchResult>()
	      {
	         @Override
	         protected StockMovementSearchResult call() throws Exception
	         {
	            return serviceService.periodicalMovement(data);
	         }
	      };
	   }

}
