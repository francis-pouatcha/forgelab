package org.adorsys.adpharma.client.jpa.disbursement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgencyController;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerCreateView;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DisbursementAgencyCreateController extends DisbursementAgencyController
{

	   @Inject
	   DisbursementCreateView createView;

	   
	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent Disbursement   model)
	   {
	      this.sourceEntity = model;
	      bind(createView.getView().getCashOutAgencySelection(), createView.getView().getCashOutAgencyForm());
	      activateButton(createView.getView().getCashOutAgencySelection());
	   }

}
