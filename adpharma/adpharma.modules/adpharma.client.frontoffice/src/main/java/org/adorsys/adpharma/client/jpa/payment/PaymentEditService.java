package org.adorsys.adpharma.client.jpa.payment;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentEditService extends Service<Payment>
{

   @Inject
   private PaymentService remoteService;

   private Payment entity;

   public PaymentEditService setPayment(Payment entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
