package org.adorsys.adpharma.client.jpa.disbursement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class DisbursementProcessService extends Service<Disbursement>{
	 private Disbursement model;

	   @Inject
	   private DisbursementService remoteService;

	   public DisbursementProcessService setModel(Disbursement model)
	   {
	      this.model = model;
	      return this;
	   }

	   @Override
	   protected Task<Disbursement> createTask()
	   {
	      return new Task<Disbursement>()
	      {
	         @Override
	         protected Disbursement call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.create(model);
	         }
	      };
	   }
}
