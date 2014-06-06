package org.adorsys.adpharma.client.jpa.paymentitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PaymentItemIntialScreenController extends InitialScreenController<PaymentItem>
{
   @Override
   public PaymentItem newEntity()
   {
      return new PaymentItem();
   }
}
