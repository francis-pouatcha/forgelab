package org.adorsys.adpharma.client.jpa.inventoryitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryItemRecordingUserEditController extends InventoryItemRecordingUserController
{

   @Inject
   InventoryItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent InventoryItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getInventoryItemRecordingUserSelection());
      bind(editView.getView().getInventoryItemRecordingUserSelection());
   }
}
