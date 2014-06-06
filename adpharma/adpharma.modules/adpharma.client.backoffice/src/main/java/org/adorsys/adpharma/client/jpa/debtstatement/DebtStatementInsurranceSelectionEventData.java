package org.adorsys.adpharma.client.jpa.debtstatement;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class DebtStatementInsurranceSelectionEventData extends
      AssocSelectionEventData<Customer>
{

   public DebtStatementInsurranceSelectionEventData(Object id, Object sourceEntity,
         Customer targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
