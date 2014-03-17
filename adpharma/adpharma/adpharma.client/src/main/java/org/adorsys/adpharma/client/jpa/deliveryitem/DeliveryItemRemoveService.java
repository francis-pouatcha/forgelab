package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public class DeliveryItemRemoveService extends Service<DeliveryItem>
{

   @Inject
   private DeliveryItemService remoteService;

   private DeliveryItem entity;

   public DeliveryItemRemoveService setEntity(DeliveryItem entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
