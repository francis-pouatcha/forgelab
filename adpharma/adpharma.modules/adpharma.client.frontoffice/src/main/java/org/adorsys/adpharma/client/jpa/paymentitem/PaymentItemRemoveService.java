package org.adorsys.adpharma.client.jpa.paymentitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentItemRemoveService extends Service<PaymentItem>
{

   @Inject
   private PaymentItemService remoteService;

   private PaymentItem entity;

   public PaymentItemRemoveService setEntity(PaymentItem entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
