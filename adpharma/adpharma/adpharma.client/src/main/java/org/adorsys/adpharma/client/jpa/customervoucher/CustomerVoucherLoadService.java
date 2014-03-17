package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerVoucherLoadService extends Service<CustomerVoucher>
{

   @Inject
   private CustomerVoucherService remoteService;

   private Long id;

   public CustomerVoucherLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<CustomerVoucher> createTask()
   {
      return new Task<CustomerVoucher>()
      {
         @Override
         protected CustomerVoucher call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
