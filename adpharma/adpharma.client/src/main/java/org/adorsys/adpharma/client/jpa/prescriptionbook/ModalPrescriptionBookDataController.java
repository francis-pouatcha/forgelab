package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

@Singleton
public class ModalPrescriptionBookDataController {
	
	@Inject
	ModalPrescriptionBookRepportDataView view;
	
	@Inject
	PeriodicalPrescriptionBookDataSearchInput model;
	
	@Inject
	PrescriptionBookPeriodicalSearchService prescriptionBookPeriodicalSearchService;
	
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	
	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		
		
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				view.closeDialog();
			}
		});
		
		prescriptionBookPeriodicalSearchService.setOnFailed(callFailedEventHandler);
		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Set<ConstraintViolation<PeriodicalPrescriptionBookDataSearchInput>> validate = view.validate(model);
				if(validate.isEmpty()) {
					prescriptionBookPeriodicalSearchService.setSearchInputs(model).start();
				}
			}
		});
		
		prescriptionBookPeriodicalSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
			PrescriptionBookPeriodicalSearchService source=	(PrescriptionBookPeriodicalSearchService)event.getSource();
			List<PrescriptionBook> resultList = source.getValue().getResultList();
			event.consume();
			source.reset();
			LoginAgency agency = securityUtil.getAgency();
			Login connectedUser = securityUtil.getConnectedUser();
			
			}
		});
		
		view.addValidators();
		
	}
	
	
	
	public void handlePrescriptionBookRepportSearchRequestEvent(@Observes @EntitySearchRequestedEvent PeriodicalPrescriptionBookDataSearchInput data) {
		PropertyReader.copy(data, model);
		view.showDiaLog();
	}
	
	

}
