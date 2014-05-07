package org.adorsys.adpharma.client.jpa.payment;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PaymentCreateService extends Service<Payment>
{

   private Payment model;

   @Inject
   private PaymentService remoteService;

   public PaymentCreateService setModel(Payment model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<Payment> createTask()
   {
      return new Task<Payment>()
      {
         @Override
         protected Payment call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
