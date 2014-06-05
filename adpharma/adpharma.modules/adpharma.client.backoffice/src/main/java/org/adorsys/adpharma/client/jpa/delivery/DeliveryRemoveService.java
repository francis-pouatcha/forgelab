package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DeliveryRemoveService extends Service<Delivery>
{

   @Inject
   private DeliveryService remoteService;

   private Delivery entity;

   public DeliveryRemoveService setEntity(Delivery entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
