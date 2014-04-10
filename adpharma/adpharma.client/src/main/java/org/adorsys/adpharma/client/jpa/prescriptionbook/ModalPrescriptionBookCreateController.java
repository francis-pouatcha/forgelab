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

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
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
