package org.adorsys.adpharma.client.jpa.payment;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentRemoveService extends Service<Payment>
{

   @Inject
   private PaymentService remoteService;

   private Payment entity;

   public PaymentRemoveService setEntity(Payment entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
