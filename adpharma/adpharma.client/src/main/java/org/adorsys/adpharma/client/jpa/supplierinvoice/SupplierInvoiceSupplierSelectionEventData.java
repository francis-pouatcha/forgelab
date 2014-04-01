package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierInvoiceSupplierSelectionEventData extends
      AssocSelectionEventData<Supplier>
{

   public SupplierInvoiceSupplierSelectionEventData(Object id, Object sourceEntity,
         Supplier targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
