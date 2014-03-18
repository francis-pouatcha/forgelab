package org.adorsys.adpharma.client.jpa.stockmovement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class StockMovementCreatingUserCreateController extends StockMovementCreatingUserController
{

   @Inject
   StockMovementCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent StockMovement model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getStockMovementCreatingUserSelection());
      activateButton(createView.getView().getStockMovementCreatingUserSelection());
   }
}