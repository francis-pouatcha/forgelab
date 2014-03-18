package org.adorsys.adpharma.client.jpa.inventoryitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class InventoryItemArticleCreateController extends InventoryItemArticleController
{

   @Inject
   InventoryItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent InventoryItem model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getInventoryItemArticleSelection());
      activateButton(createView.getView().getInventoryItemArticleSelection());
   }
}
