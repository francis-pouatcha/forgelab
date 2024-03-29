package org.adorsys.adpharma.client.jpa.inventoryitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InventoryItemArticleDisplayController extends InventoryItemArticleController
{

   @Inject
   private InventoryItemDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent InventoryItem model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getInventoryItemArticleSelection(), displayView.getView().getInventoryItemArticleForm());
      bind(displayView.getView().getInventoryItemArticleSelection(), displayView.getView().getInventoryItemArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent InventoryItem selectedEntity)
   {
      loadAssociation();
   }
}
