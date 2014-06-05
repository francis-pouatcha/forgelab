package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderItemSearchService extends Service<SalesOrderItemSearchResult>
{

   @Inject
   private SalesOrderItemService remoteService;

   private SalesOrderItemSearchInput searchInputs;

   public SalesOrderItemSearchService setSearchInputs(SalesOrderItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SalesOrderItemSearchResult> createTask()
   {
      return new Task<SalesOrderItemSearchResult>()
      {
         @Override
         protected SalesOrderItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
