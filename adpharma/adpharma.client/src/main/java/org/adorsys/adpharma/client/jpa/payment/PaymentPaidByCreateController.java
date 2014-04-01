package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PaymentPaidByCreateController extends PaymentPaidByController
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
      activateButton(createView.getView().getPaymentPaidBySelection(), createView.getView().getPaymentPaidByForm());
      bind(createView.getView().getPaymentPaidBySelection(), createView.getView().getPaymentPaidByForm());
   }
}
