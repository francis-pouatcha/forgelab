package org.adorsys.adpharma.client.jpa.paymentitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PaymentItemPaidByCreateController extends PaymentItemPaidByController
{

   @Inject
   PaymentItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent PaymentItem model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getPaymentItemPaidBySelection(), createView.getView().getPaymentItemPaidByForm());
      bind(createView.getView().getPaymentItemPaidBySelection(), createView.getView().getPaymentItemPaidByForm());
   }
}
