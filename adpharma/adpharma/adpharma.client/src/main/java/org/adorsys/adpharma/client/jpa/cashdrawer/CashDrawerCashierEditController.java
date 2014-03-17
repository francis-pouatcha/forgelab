package org.adorsys.adpharma.client.jpa.cashdrawer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CashDrawerCashierEditController extends CashDrawerCashierController
{

   @Inject
   CashDrawerEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent CashDrawer model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCashDrawerCashierSelection());
      bind(editView.getView().getCashDrawerCashierSelection());
   }
}
