package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderManagedLotService extends Service<Boolean>
{

	   @Inject
	   private SalesOrderService remoteService;

	   @Override
	   protected Task<Boolean> createTask()
	   {
	      return new Task<Boolean>()
	      {
	         @Override
	         protected Boolean call() throws Exception
	         {
	            return remoteService.isManagedLot();
	         }
	      };
	   }

}
