package org.adorsys.adpharma.client.jpa.paymentitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;

public class PaymentItemEditService extends Service<PaymentItem>
{

   @Inject
   private PaymentItemService remoteService;

   private PaymentItem entity;

   public PaymentItemEditService setPaymentItem(PaymentItem entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<PaymentItem> createTask()
   {
      return new Task<PaymentItem>()
      {
         @Override
         protected PaymentItem call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
