package org.adorsys.adpharma.client.jpa.insurrance;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ModalInsurranceInsurerCreateController extends InsurranceInsurerCreateController {

	   @Inject
	   ModalInsurranceCreateView modalInsurranceCreateView;

	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent Insurrance model)
	   {
	      this.sourceEntity = model;
	      bind(modalInsurranceCreateView.getView().getInsurranceInsurerSelection(), modalInsurranceCreateView.getView().getInsurranceInsurerForm());
	      activateButton(modalInsurranceCreateView.getView().getInsurranceInsurerSelection());
	   }
}
