package org.adorsys.adpharma.client.jpa.inventory;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryAgencyDisplayController extends InventoryAgencyController
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
      disableButton(displayView.getView().getInventoryAgencySelection());
      bind(displayView.getView().getInventoryAgencySelection(), displayView.getView().getInventoryAgencyForm());
   }
}
