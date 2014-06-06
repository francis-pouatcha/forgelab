package org.adorsys.adpharma.client.jpa.disbursement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class DisbursementCashierCreateController extends DisbursementCashierController
{

	   @Inject
	   DisbursementCreateView createView;

	   
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
