package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerVoucherEditService extends Service<CustomerVoucher>
{

   @Inject
   private CustomerVoucherService remoteService;

   private CustomerVoucher entity;

   public CustomerVoucherEditService setCustomerVoucher(CustomerVoucher entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
