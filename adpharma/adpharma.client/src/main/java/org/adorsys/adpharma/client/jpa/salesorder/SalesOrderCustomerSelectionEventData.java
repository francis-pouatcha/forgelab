package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class SalesOrderCustomerSelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public SalesOrderCustomerSelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
