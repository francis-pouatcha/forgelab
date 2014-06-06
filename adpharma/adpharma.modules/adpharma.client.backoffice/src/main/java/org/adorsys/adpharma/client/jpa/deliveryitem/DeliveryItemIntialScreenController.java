package org.adorsys.adpharma.client.jpa.deliveryitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DeliveryItemIntialScreenController extends InitialScreenController<DeliveryItem>
{
   @Override
   public DeliveryItem newEntity()
   {
      return new DeliveryItem();
   }
}
