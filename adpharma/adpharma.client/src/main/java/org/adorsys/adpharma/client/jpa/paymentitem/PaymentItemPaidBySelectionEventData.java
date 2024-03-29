package org.adorsys.adpharma.client.jpa.paymentitem;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class PaymentItemPaidBySelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public PaymentItemPaidBySelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
