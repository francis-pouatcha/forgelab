package org.adorsys.adpharma.client.jpa.inventoryitem;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryItemArticleEditController extends InventoryItemArticleController
{

   @Inject
   InventoryItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent InventoryItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getInventoryItemArticleSelection(), editView.getView().getInventoryItemArticleForm());
      bind(editView.getView().getInventoryItemArticleSelection(), editView.getView().getInventoryItemArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent InventoryItem p)
   {
      loadAssociation();
   }

}
