package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

@Singleton
public class DeliveryItemCreateService extends Service<DeliveryItem>
{

   private DeliveryItem model;

   @Inject
   private DeliveryItemService remoteService;

   public DeliveryItemCreateService setModel(DeliveryItem model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<DeliveryItem> createTask()
   {
      return new Task<DeliveryItem>()
      {
         @Override
         protected DeliveryItem call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
