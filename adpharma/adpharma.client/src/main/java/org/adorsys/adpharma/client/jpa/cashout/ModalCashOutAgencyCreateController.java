package org.adorsys.adpharma.client.jpa.cashout;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ModalCashOutAgencyCreateController  extends CashOutAgencyController
{

	   @Inject
	   ModalCashOutCreateView modalCreateView;

	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent CashOut  model)
	   {
	      this.sourceEntity = model;
	      bind(modalCreateView.getView().getCashOutAgencySelection(), modalCreateView.getView().getCashOutAgencyForm());
	      activateButton(modalCreateView.getView().getCashOutAgencySelection());
	   }

}
