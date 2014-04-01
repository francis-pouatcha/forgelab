package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherRecordingUserEditController extends CustomerVoucherRecordingUserController
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
      activateButton(editView.getView().getCustomerVoucherRecordingUserSelection());
      bind(editView.getView().getCustomerVoucherRecordingUserSelection(), editView.getView().getCustomerVoucherRecordingUserForm());
   }
}
