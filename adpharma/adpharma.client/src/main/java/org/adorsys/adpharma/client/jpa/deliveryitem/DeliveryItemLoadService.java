package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DeliveryItemLoadService extends Service<DeliveryItem>
{

   @Inject
   private DeliveryItemService remoteService;

   private Long id;

   public DeliveryItemLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
