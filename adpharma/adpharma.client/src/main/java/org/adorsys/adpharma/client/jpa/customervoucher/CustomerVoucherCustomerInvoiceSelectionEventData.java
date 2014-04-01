package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerVoucherCustomerInvoiceSelectionEventData extends
      AssocSelectionEventData<CustomerInvoice>
{

   public CustomerVoucherCustomerInvoiceSelectionEventData(Object id, Object sourceEntity,
         CustomerInvoice targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
