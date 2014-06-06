package org.adorsys.adpharma.client.jpa.deliveryitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DeliveryItemCreatingUserEditController extends DeliveryItemCreatingUserController
{

   @Inject
   DeliveryItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent DeliveryItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDeliveryItemCreatingUserSelection());
      bind(editView.getView().getDeliveryItemCreatingUserSelection(), editView.getView().getDeliveryItemCreatingUserForm());
   }
}
