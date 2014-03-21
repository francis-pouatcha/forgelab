package org.adorsys.adpharma.client.jpa.delivery;

import javax.inject.Inject;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DeliveryCloseService extends Service<Delivery> {
	@Inject
	   private DeliveryService remoteService;

	   private Delivery entity;

	   public DeliveryCloseService setDelivery(Delivery entity)
	   {
	      this.entity = entity;
	      return this;
	   }

	   @Override
	   protected Task<Delivery> createTask()
	   {
	      return new Task<Delivery>()
	      {
	         @Override
	         protected Delivery call() throws Exception
	         {
	            if (entity == null)
	               return null;
	            return remoteService.saveAndClose(entity);
	         }
	      };
	   }
}
