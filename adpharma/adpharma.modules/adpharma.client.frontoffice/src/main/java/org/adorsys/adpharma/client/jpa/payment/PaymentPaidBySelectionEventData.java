package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class PaymentPaidBySelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public PaymentPaidBySelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
