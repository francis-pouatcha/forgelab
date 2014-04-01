package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class PaymentPaidBySelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public PaymentPaidBySelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
