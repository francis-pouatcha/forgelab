package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.hospital.HospitalCreateService;
import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.adpharma.client.jpa.prescriber.PrescriberCreateService;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalPrescriptionBookCreateController {
	@Inject
	private ModalPrescriptionBookCreateView  modalPrescriptionBookCreateView ;

	@Inject
	private PrescriptionBookCreateService prescriptionBookCreateService ;

	@Inject
	private PrescriptionBookEditService prescriptionBookEditService ;

	@Inject   
	ServiceCallFailedEventHandler serviceCallFailedEventHandler ;

	@Inject
	@Bundle({ CrudKeys.class, PrescriptionBook.class })
	private ResourceBundle resourceBundle;

	@Inject
	@CreateModelEvent 
	private Event<PrescriptionBook> createModelEvent;

	@Inject
	private PrescriptionBook model ;

	@Inject
	@ModalEntityCreateDoneEvent
	private Event<PrescriptionBook> modalPrescriptionBookCreateDoneEvent;
	
	@Inject
	private HospitalCreateService hospitalCreateService ;
	
	@Inject
	private PrescriberCreateService prescriberCreateService ;

	@PostConstruct
	public void postConstruct(){          

		modalPrescriptionBookCreateView.bind(model);
		//		handle cancel action
		modalPrescriptionBookCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modalPrescriptionBookCreateView.closeDialog();

			}
		});
		
		// handle hopital creation 
		modalPrescriptionBookCreateView.getCreateHospitalButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String hospitalName = Dialogs.create().title("Saisir le Nom de l'hopital").message("Hopital :").showTextInput();
				if(StringUtils.isNotBlank(hospitalName)){
					Hospital hospital = new Hospital();
					hospital.setName(hospitalName.toUpperCase());
					hospitalCreateService.setModel(hospital).start();
				}
				
			}
		});
		hospitalCreateService.setOnFailed(serviceCallFailedEventHandler);
		hospitalCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				HospitalCreateService s = (HospitalCreateService) event.getSource();
				Hospital hospital = s.getValue();
				event.consume();
				s.reset();
				PrescriptionBookHospital prescriptionBookHospital = new PrescriptionBookHospital(hospital);
				modalPrescriptionBookCreateView.getView().getPrescriptionBookHospitalSelection().getHospital().getItems().clear();
				modalPrescriptionBookCreateView.getView().getPrescriptionBookHospitalSelection().getHospital().getItems().add(prescriptionBookHospital);
				modalPrescriptionBookCreateView.getView().getPrescriptionBookHospitalSelection().getHospital().setValue(prescriptionBookHospital);
			}
		});
		
		// handle prescriber creation 
				modalPrescriptionBookCreateView.getCreatePrescriberButton().setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						String prescriberName = Dialogs.create().title("Saisir le Nom du Prescripteur").message("Prescripteur :").showTextInput();
						if(StringUtils.isNotBlank(prescriberName)){
							Prescriber prescriber = new Prescriber();
							prescriber.setName(prescriberName.toUpperCase());
							prescriberCreateService.setModel(prescriber).start();
						}
						
					}
				});
				prescriberCreateService.setOnFailed(serviceCallFailedEventHandler);
				prescriberCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					
					@Override
					public void handle(WorkerStateEvent event) {
						PrescriberCreateService s = (PrescriberCreateService) event.getSource();
						Prescriber prescriber = s.getValue();
						event.consume();
						s.reset();
						PrescriptionBookPrescriber prescriptionBookPrescriber = new PrescriptionBookPrescriber(prescriber);
						modalPrescriptionBookCreateView.getView().getPrescriptionBookPrescriberSelection().getPrescriber().getItems().clear();
						modalPrescriptionBookCreateView.getView().getPrescriptionBookPrescriberSelection().getPrescriber().getItems().add(prescriptionBookPrescriber);
						modalPrescriptionBookCreateView.getView().getPrescriptionBookPrescriberSelection().getPrescriber().setValue(prescriptionBookPrescriber);
					}
				});
		//  handele create action
		modalPrescriptionBookCreateView.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PropertyReader.copy(new PrescriptionBook(), model);
			}
		});
		//  handle save action
		modalPrescriptionBookCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<PrescriptionBook>> violations = modalPrescriptionBookCreateView.getView().validate(model);
				if (violations.isEmpty())
				{
					if(model.getId()!=null){
						prescriptionBookEditService.setPrescriptionBook(model).start();
					}else {
						prescriptionBookCreateService.setModel(model).start();
					}
				}
				else
				{
					Dialogs.create().title(resourceBundle.getString("Entity_create_error.title"))
					.message(violations.toString()).showError();
				}
			}

		});


		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().title(resourceBundle.getString("Entity_create_error.title")).showException(exception);

			}
		});

		//		  handle created call 
		prescriptionBookCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PrescriptionBookCreateService s = (PrescriptionBookCreateService) event.getSource();
				PrescriptionBook prescriptionBook = s.getValue();
				event.consume();
				s.reset();
				handlePrescriptionProcessingResult(prescriptionBook);

			}
		});

		prescriptionBookCreateService.setOnFailed(serviceCallFailedEventHandler);

		//		  handle created call 
		prescriptionBookEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PrescriptionBookEditService s = (PrescriptionBookEditService) event.getSource();
				PrescriptionBook prescriptionBook = s.getValue();
				event.consume();
				s.reset();
				handlePrescriptionProcessingResult(prescriptionBook);

			}
		});

		prescriptionBookEditService.setOnFailed(serviceCallFailedEventHandler);

		createModelEvent.fire(model);

	}


	public PrescriptionBook getModel() {
		return model;
	}
	public void handleModalPrescriptionBookCreateEvent(@Observes @ModalEntityCreateRequestedEvent PrescriptionBook prescriptionBook){
		PropertyReader.copy(prescriptionBook, model);
		modalPrescriptionBookCreateView.showDiaLog();
	}

	public void	handlePrescriptionProcessingResult(PrescriptionBook prescriptionBook){
		modalPrescriptionBookCreateView.closeDialog();
		modalPrescriptionBookCreateDoneEvent.fire(prescriptionBook);
	}
}
