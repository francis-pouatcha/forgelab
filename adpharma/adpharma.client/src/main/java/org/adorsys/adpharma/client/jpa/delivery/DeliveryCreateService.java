package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

@Singleton
public class DeliveryCreateService extends Service<Delivery>
{

   private Delivery model;

   @Inject
   private DeliveryService remoteService;

   public DeliveryCreateService setModel(Delivery model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
