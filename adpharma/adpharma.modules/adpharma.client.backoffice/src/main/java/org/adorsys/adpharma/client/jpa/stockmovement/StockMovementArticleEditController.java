package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class StockMovementArticleEditController extends StockMovementArticleController
{

   @Inject
   StockMovementEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent StockMovement model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getStockMovementArticleSelection(), editView.getView().getStockMovementArticleForm());
      bind(editView.getView().getStockMovementArticleSelection(), editView.getView().getStockMovementArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent StockMovement p)
   {
      loadAssociation();
   }

}
