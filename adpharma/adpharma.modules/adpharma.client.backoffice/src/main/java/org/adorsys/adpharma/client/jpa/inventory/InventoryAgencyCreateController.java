package org.adorsys.adpharma.client.jpa.inventory;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class InventoryAgencyCreateController extends InventoryAgencyController
{

   @Inject
   InventoryCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Inventory model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getInventoryAgencySelection(), createView.getView().getInventoryAgencyForm());
      activateButton(createView.getView().getInventoryAgencySelection());
   }
}
