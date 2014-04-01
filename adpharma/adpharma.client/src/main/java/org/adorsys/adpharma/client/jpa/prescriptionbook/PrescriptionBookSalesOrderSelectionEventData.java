package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class PrescriptionBookSalesOrderSelectionEventData extends
      AssocSelectionEventData<SalesOrder>
{

   public PrescriptionBookSalesOrderSelectionEventData(Object id, Object sourceEntity,
         SalesOrder targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
