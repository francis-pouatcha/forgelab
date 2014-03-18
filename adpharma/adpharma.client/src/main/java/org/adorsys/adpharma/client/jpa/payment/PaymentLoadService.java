package org.adorsys.adpharma.client.jpa.payment;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentLoadService extends Service<Payment>
{

   @Inject
   private PaymentService remoteService;

   private Long id;

   public PaymentLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
