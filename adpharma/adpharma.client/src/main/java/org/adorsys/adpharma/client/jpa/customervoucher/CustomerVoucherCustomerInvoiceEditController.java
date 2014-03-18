package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherCustomerInvoiceEditController extends CustomerVoucherCustomerInvoiceController
{

   @Inject
   CustomerVoucherEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerVoucher model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerVoucherCustomerInvoiceSelection());
      bind(editView.getView().getCustomerVoucherCustomerInvoiceSelection());
   }
}
