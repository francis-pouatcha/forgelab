package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherCustomerInvoiceEditController extends CustomerVoucherCustomerInvoiceController
{

   @Inject
   CustomerVoucherEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerVoucher model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerVoucherCustomerInvoiceSelection(), editView.getView().getCustomerVoucherCustomerInvoiceForm());
      bind(editView.getView().getCustomerVoucherCustomerInvoiceSelection(), editView.getView().getCustomerVoucherCustomerInvoiceForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent CustomerVoucher p)
   {
      loadAssociation();
   }

}
