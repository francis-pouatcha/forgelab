package org.adorsys.adpharma.client.jpa.delivery;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DeliveryCreatingUserCreateController extends DeliveryCreatingUserController
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
      bind(createView.getView().getDeliveryCreatingUserSelection());
      activateButton(createView.getView().getDeliveryCreatingUserSelection());
   }
}
