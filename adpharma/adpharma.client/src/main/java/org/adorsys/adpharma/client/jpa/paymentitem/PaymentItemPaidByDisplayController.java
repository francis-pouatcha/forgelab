package org.adorsys.adpharma.client.jpa.paymentitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PaymentItemPaidByDisplayController extends PaymentItemPaidByController
{

   @Inject
   private PaymentItemDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent PaymentItem model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getPaymentItemPaidBySelection(), displayView.getView().getPaymentItemPaidByForm());
      bind(displayView.getView().getPaymentItemPaidBySelection(), displayView.getView().getPaymentItemPaidByForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent PaymentItem selectedEntity)
   {
      loadAssociation();
   }
}
