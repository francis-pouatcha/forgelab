package org.adorsys.adpharma.client.jpa.payment;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentPaidByEditController extends PaymentPaidByController
{

   @Inject
   PaymentEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent Payment model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPaymentPaidBySelection(), editView.getView().getPaymentPaidByForm());
      bind(editView.getView().getPaymentPaidBySelection(), editView.getView().getPaymentPaidByForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent Payment p)
   {
      loadAssociation();
   }

}
