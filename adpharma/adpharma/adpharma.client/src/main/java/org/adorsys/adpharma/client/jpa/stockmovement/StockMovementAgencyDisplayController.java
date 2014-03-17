package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class StockMovementAgencyDisplayController extends StockMovementAgencyController
{

   @Inject
   private StockMovementDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent StockMovement model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getStockMovementAgencySelection());
   }
}
