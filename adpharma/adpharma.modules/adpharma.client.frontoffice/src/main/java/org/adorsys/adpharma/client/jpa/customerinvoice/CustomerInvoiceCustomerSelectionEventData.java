package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class CustomerInvoiceCustomerSelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public CustomerInvoiceCustomerSelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
