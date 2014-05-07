package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherCustomerEditController extends CustomerVoucherCustomerController
{

   @Inject
   CustomerVoucherEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerVoucher model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerVoucherCustomerSelection(), editView.getView().getCustomerVoucherCustomerForm());
      bind(editView.getView().getCustomerVoucherCustomerSelection(), editView.getView().getCustomerVoucherCustomerForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent CustomerVoucher p)
   {
      loadAssociation();
   }

}
