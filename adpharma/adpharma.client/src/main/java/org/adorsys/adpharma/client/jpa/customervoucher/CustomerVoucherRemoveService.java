package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerVoucherRemoveService extends Service<CustomerVoucher>
{

   @Inject
   private CustomerVoucherService remoteService;

   private CustomerVoucher entity;

   public CustomerVoucherRemoveService setEntity(CustomerVoucher entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
