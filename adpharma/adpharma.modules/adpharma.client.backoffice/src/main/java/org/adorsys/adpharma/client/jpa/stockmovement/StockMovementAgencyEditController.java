package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class StockMovementAgencyEditController extends StockMovementAgencyController
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
      activateButton(editView.getView().getStockMovementAgencySelection());
      bind(editView.getView().getStockMovementAgencySelection(), editView.getView().getStockMovementAgencyForm());
   }
}
