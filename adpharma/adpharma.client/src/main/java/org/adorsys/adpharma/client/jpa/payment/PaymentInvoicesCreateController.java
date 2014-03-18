package org.adorsys.adpharma.client.jpa.payment;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PaymentInvoicesCreateController extends PaymentInvoicesController
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
      disableButton(createView.getView().getPaymentInvoicesSelection(), createView.getView().getPaymentInvoicesForm());
      bind(createView.getView().getPaymentInvoicesSelection(), createView.getView().getPaymentInvoicesForm());
   }
}
