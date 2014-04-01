package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class CustomerInvoiceInsuranceSelectionEventData extends
      AssocSelectionEventData<Insurrance>
{

   public CustomerInvoiceInsuranceSelectionEventData(Object id, Object sourceEntity,
         Insurrance targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
