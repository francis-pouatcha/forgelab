package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CashDrawerLoadOpenService extends Service<CashDrawerSearchResult>
{
	   @Inject
	   private CashDrawerService remoteService;

	   @Override
	   protected Task<CashDrawerSearchResult> createTask()
	   {
	      return new Task<CashDrawerSearchResult>()
	      {
	         @Override
	         protected CashDrawerSearchResult call() throws Exception
	         {
	            return remoteService.myOpenDrawers();
	         }
	      };
	   }

}
