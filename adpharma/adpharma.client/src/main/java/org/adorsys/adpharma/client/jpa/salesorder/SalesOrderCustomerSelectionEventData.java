package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class SalesOrderCustomerSelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public SalesOrderCustomerSelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
