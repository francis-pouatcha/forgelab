package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DeliveryIntialScreenController extends InitialScreenController<Delivery>
{
   @Override
   public Delivery newEntity()
   {
      return new Delivery();
   }
}
