package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class SalesOrderInsuranceSelectionEventData extends
      AssocSelectionEventData<Insurrance>
{

   public SalesOrderInsuranceSelectionEventData(Object id, Object sourceEntity,
         Insurrance targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
