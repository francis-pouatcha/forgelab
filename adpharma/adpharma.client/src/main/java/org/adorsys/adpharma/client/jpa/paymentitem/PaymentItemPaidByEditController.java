package org.adorsys.adpharma.client.jpa.paymentitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentItemPaidByEditController extends PaymentItemPaidByController
{

   @Inject
   PaymentItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent PaymentItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPaymentItemPaidBySelection(), editView.getView().getPaymentItemPaidByForm());
      bind(editView.getView().getPaymentItemPaidBySelection(), editView.getView().getPaymentItemPaidByForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent PaymentItem p)
   {
      loadAssociation();
   }

}
