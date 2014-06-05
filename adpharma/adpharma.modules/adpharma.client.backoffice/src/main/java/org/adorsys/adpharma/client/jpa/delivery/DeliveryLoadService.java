package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DeliveryLoadService extends Service<Delivery>
{

   @Inject
   private DeliveryService remoteService;

   private Long id;

   public DeliveryLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
