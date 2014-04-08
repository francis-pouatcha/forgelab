package org.adorsys.adpharma.client.jpa.paymentitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentItemLoadService extends Service<PaymentItem>
{

   @Inject
   private PaymentItemService remoteService;

   private Long id;

   public PaymentItemLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
