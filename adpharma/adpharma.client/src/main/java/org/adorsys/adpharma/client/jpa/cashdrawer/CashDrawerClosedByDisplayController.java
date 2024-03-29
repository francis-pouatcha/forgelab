package org.adorsys.adpharma.client.jpa.cashdrawer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CashDrawerClosedByDisplayController extends CashDrawerClosedByController
{

   @Inject
   private CashDrawerDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent CashDrawer model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getCashDrawerClosedBySelection());
      bind(displayView.getView().getCashDrawerClosedBySelection(), displayView.getView().getCashDrawerClosedByForm());
   }
}
