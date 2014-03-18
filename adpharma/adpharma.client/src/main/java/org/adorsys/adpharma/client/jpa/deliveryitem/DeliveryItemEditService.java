package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public class DeliveryItemEditService extends Service<DeliveryItem>
{

   @Inject
   private DeliveryItemService remoteService;

   private DeliveryItem entity;

   public DeliveryItemEditService setDeliveryItem(DeliveryItem entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
