package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class SalesOrderSalesAgentSelectionEventData extends
      AssocSelectionEventData<Login>
{

   public SalesOrderSalesAgentSelectionEventData(Object id, Object sourceEntity,
         Login targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
