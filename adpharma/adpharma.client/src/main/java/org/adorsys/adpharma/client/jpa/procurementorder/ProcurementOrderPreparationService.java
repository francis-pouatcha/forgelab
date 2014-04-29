package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderPreparationService extends Service<ProcurementOrder>
{

	   @Inject
	   private ProcurementOrderService remoteService;

	   private ProcurementOrderPreparationData entity;

	   public ProcurementOrderPreparationService setEntity(ProcurementOrderPreparationData entity)
	   {
	      this.entity = entity;
	      return this;
	   }

	   @Override
	   protected Task<ProcurementOrder> createTask()
	   {
	      return new Task<ProcurementOrder>()
	      {
	         @Override
	         protected ProcurementOrder call() throws Exception
	         {
	            return remoteService.proccessPreparation(entity);
	         }
	      };
	   }
	}
