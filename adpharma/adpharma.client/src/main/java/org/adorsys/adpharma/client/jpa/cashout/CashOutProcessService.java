package org.adorsys.adpharma.client.jpa.cashout;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashOutProcessService extends Service<CashOut>{
	 private CashOut model;

	   @Inject
	   private CashOutService remoteService;

	   public CashOutProcessService setModel(CashOut model)
	   {
	      this.model = model;
	      return this;
	   }

	   @Override
	   protected Task<CashOut> createTask()
	   {
	      return new Task<CashOut>()
	      {
	         @Override
	         protected CashOut call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.processCashOut(model);
	         }
	      };
	   }
}
