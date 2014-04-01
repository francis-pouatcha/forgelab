package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class StockMovementCreatingUserEditController extends StockMovementCreatingUserController
{

   @Inject
   StockMovementEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent StockMovement model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getStockMovementCreatingUserSelection());
      bind(editView.getView().getStockMovementCreatingUserSelection(), editView.getView().getStockMovementCreatingUserForm());
   }
}
