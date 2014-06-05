package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerVoucherCreateService extends Service<CustomerVoucher>
{

   private CustomerVoucher model;

   @Inject
   private CustomerVoucherService remoteService;

   public CustomerVoucherCreateService setModel(CustomerVoucher model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
