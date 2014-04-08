package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PaymentPaymentItemsCreateController extends PaymentPaymentItemsController
{

   @Inject
   PaymentCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Payment model)
   {
      this.sourceEntity = model;
      disableButton(createView.getView().getPaymentPaymentItemsSelection(), createView.getView().getPaymentPaymentItemsForm());
      bind(createView.getView().getPaymentPaymentItemsSelection(), createView.getView().getPaymentPaymentItemsForm());
   }
}
