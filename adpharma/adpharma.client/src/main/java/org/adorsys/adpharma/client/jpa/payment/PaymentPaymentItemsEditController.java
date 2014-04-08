package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentPaymentItemsEditController extends PaymentPaymentItemsController
{

   @Inject
   PaymentEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Payment model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPaymentPaymentItemsSelection(), editView.getView().getPaymentPaymentItemsForm());
      bind(editView.getView().getPaymentPaymentItemsSelection(), editView.getView().getPaymentPaymentItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent Payment p)
   {
      loadAssociation();
   }
}
