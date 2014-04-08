package org.adorsys.adpharma.client.jpa.cashout;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class CashOutCashierCreateController extends CashOutCashierController
{

	   @Inject
	   CashOutCreateView createView;

	   
	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent Login model)
	   {
	      this.sourceEntity = model;
	      bind(createView.getView().getCashOutCashierSelection(), createView.getView().getCashOutCashierForm());
	      activateButton(createView.getView().getCashOutCashierSelection());
	   }

}
