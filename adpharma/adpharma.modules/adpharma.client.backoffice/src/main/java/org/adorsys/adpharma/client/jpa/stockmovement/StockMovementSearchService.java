package org.adorsys.adpharma.client.jpa.stockmovement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class StockMovementSearchService extends Service<StockMovementSearchResult>
{

   @Inject
   private StockMovementService remoteService;

   private StockMovementSearchInput searchInputs;

   public StockMovementSearchService setSearchInputs(StockMovementSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
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
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
