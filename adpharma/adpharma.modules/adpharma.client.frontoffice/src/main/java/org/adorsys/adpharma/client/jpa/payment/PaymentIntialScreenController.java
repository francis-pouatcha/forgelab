package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PaymentIntialScreenController extends InitialScreenController<Payment>
{
   @Override
   public Payment newEntity()
   {
      return new Payment();
   }
}
