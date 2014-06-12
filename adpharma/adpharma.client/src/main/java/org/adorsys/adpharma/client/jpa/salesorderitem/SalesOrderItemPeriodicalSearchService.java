package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;

public class SalesOrderItemPeriodicalSearchService extends Service<SalesOrderItemSearchResult>
{

	   @Inject
	   private SalesOrderItemService remoteService;

	   private PeriodicalDataSearchInput searchInputs;

	   public SalesOrderItemPeriodicalSearchService setSearchInputs(PeriodicalDataSearchInput searchInputs)
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
	            return remoteService.periodicalSales(searchInputs);
	         }
	      };
	   }

}
