package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CustomerVoucherIntialScreenController extends InitialScreenController<CustomerVoucher>
{
   @Override
   public CustomerVoucher newEntity()
   {
      return new CustomerVoucher();
   }
}
