package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CashDrawerLoadOpenService extends Service<CashDrawer>
{

	   @Inject
	   private CashDrawerService remoteService;

	   @Override
	   protected Task<CashDrawer> createTask()
	   {
	      return new Task<CashDrawer>()
	      {
	         @Override
	         protected CashDrawer call() throws Exception
	         {
	            return remoteService.loadOpenCashDrawer();
	         }
	      };
	   }

}
