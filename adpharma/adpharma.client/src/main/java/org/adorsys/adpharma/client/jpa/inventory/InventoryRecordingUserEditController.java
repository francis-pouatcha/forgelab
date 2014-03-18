package org.adorsys.adpharma.client.jpa.inventory;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryRecordingUserEditController extends InventoryRecordingUserController
{

   @Inject
   InventoryEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Inventory model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getInventoryRecordingUserSelection());
      bind(editView.getView().getInventoryRecordingUserSelection());
   }
}
