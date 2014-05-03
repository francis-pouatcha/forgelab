package org.adorsys.adpharma.client.jpa.deliveryitem;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DeliveryItemArticleEditController extends DeliveryItemArticleController
{

   @Inject
   DeliveryItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent DeliveryItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDeliveryItemArticleSelection(), editView.getView().getDeliveryItemArticleForm());
      bind(editView.getView().getDeliveryItemArticleSelection(), editView.getView().getDeliveryItemArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent DeliveryItem p)
   {
      loadAssociation();
   }

}
