package org.adorsys.adpharma.client.jpa.paymentitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;

@Singleton
public class PaymentItemCreateService extends Service<PaymentItem>
{

   private PaymentItem model;

   @Inject
   private PaymentItemService remoteService;

   public PaymentItemCreateService setModel(PaymentItem model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
