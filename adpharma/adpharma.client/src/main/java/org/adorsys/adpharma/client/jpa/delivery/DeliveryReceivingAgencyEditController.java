package org.adorsys.adpharma.client.jpa.delivery;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DeliveryReceivingAgencyEditController extends DeliveryReceivingAgencyController
{

   @Inject
   DeliveryEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Delivery model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDeliveryReceivingAgencySelection());
      bind(editView.getView().getDeliveryReceivingAgencySelection(), editView.getView().getDeliveryReceivingAgencyForm());
   }
}
