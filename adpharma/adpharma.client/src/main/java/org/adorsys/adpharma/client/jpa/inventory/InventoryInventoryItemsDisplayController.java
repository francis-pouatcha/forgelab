package org.adorsys.adpharma.client.jpa.inventory;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryInventoryItemsDisplayController extends InventoryInventoryItemsController
{

   @Inject
   private InventoryDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Inventory model)
   {
      this.sourceEntity = model;
//      disableButton(displayView.getView().getInventoryInventoryItemsSelection(), displayView.getView().getInventoryInventoryItemsForm());
//      bind(displayView.getView().getInventoryInventoryItemsSelection(), displayView.getView().getInventoryInventoryItemsForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent Inventory selectedEntity)
   {
      loadAssociation();
   }
}
