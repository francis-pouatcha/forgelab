package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class SupplierInvoiceDeliverySelectionEventData extends
      AssocSelectionEventData<Delivery>
{

   public SupplierInvoiceDeliverySelectionEventData(Object id, Object sourceEntity,
         Delivery targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
