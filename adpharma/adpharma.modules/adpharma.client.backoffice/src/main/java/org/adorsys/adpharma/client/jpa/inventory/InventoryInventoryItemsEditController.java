package org.adorsys.adpharma.client.jpa.inventory;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryInventoryItemsEditController extends InventoryInventoryItemsController
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
      activateButton(editView.getView().getInventoryInventoryItemsSelection(), editView.getView().getInventoryInventoryItemsForm());
      bind(editView.getView().getInventoryInventoryItemsSelection(), editView.getView().getInventoryInventoryItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent Inventory p)
   {
      loadAssociation();
   }
}
