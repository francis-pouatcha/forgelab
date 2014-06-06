package org.adorsys.adpharma.client.jpa.disbursement;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ModalDisbursementAgencyCreateController  extends DisbursementAgencyController
{

	   @Inject
	   ModalDisbursementCreateView modalCreateView;

	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent Disbursement  model)
	   {
	      this.sourceEntity = model;
	      bind(modalCreateView.getView().getCashOutAgencySelection(), modalCreateView.getView().getCashOutAgencyForm());
	      activateButton(modalCreateView.getView().getCashOutAgencySelection());
	   }

}
