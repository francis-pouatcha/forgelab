package org.adorsys.adpharma.client.jpa.delivery;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DeliveryVatDisplayController extends DeliveryVatController
{

   @Inject
   private DeliveryDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Delivery model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getDeliveryVatSelection());
      bind(displayView.getView().getDeliveryVatSelection(), displayView.getView().getDeliveryVatForm());
   }
}
