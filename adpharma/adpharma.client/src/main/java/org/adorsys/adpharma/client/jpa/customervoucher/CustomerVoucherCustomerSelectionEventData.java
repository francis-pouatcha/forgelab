package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class CustomerVoucherCustomerSelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public CustomerVoucherCustomerSelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
