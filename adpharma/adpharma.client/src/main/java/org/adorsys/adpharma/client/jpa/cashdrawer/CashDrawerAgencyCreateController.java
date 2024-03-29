package org.adorsys.adpharma.client.jpa.cashdrawer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CashDrawerAgencyCreateController extends CashDrawerAgencyController
{

   @Inject
   CashDrawerCreateView createView;

   
   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent CashDrawer model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getCashDrawerAgencySelection(), createView.getView().getCashDrawerAgencyForm());
      activateButton(createView.getView().getCashDrawerAgencySelection());
   }
}
