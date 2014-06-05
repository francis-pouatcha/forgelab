package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderSearchService extends Service<SalesOrderSearchResult>
{

   @Inject
   private SalesOrderService remoteService;

   private SalesOrderSearchInput searchInputs;

   public SalesOrderSearchService setSearchInputs(SalesOrderSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SalesOrderSearchResult> createTask()
   {
      return new Task<SalesOrderSearchResult>()
      {
         @Override
         protected SalesOrderSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
