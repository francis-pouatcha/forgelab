package org.adorsys.adpharma.client.jpa.delivery;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DeliveryDeliveryItemsCreateController extends DeliveryDeliveryItemsController
{

   @Inject
   DeliveryCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Delivery model)
   {
      this.sourceEntity = model;
      disableButton(createView.getView().getDeliveryDeliveryItemsSelection(), createView.getView().getDeliveryDeliveryItemsForm());
      bind(createView.getView().getDeliveryDeliveryItemsSelection(), createView.getView().getDeliveryDeliveryItemsForm());
   }
}
