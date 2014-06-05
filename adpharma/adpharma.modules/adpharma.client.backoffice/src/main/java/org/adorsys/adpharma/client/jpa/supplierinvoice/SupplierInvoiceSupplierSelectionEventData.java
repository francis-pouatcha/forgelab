package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class SupplierInvoiceSupplierSelectionEventData extends
      AssocSelectionEventData<Supplier>
{

   public SupplierInvoiceSupplierSelectionEventData(Object id, Object sourceEntity,
         Supplier targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
