package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentInvoicesEditController extends PaymentInvoicesController
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
      activateButton(editView.getView().getPaymentInvoicesSelection(), editView.getView().getPaymentInvoicesForm());
      bind(editView.getView().getPaymentInvoicesSelection(), editView.getView().getPaymentInvoicesForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent Payment p)
   {
      loadAssociation();
   }
}
