package org.adorsys.adpharma.client.jpa.cashout;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class CashOutCashDrawerCreateController extends CashOutCashDrawerController
{

	   @Inject
	   CashOutCreateView createView;

	   
	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent CashDrawer model)
	   {
	      this.sourceEntity = model;
	      bind(createView.getView().getCashOutCashDrawerSelection(), createView.getView().getCashOutCashDrawerForm());
	      activateButton(createView.getView().getCashOutCashDrawerSelection());
	   }

}
