package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentAgencyEditController extends PaymentAgencyController
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
      activateButton(editView.getView().getPaymentAgencySelection());
      bind(editView.getView().getPaymentAgencySelection(), editView.getView().getPaymentAgencyForm());
   }
}
