package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleCreateService;
import org.adorsys.adpharma.client.jpa.article.ModalArticleCreateView;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.controlsfx.dialog.Dialogs;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalDisbursementCreateController {
	@Inject
	private ModalDisbursementCreateView  modalCashOutCreateView ;

	@Inject
	DisbursementProcessService cashOutCreateService ;

	@Inject   
	ServiceCallFailedEventHandler serviceCallFailedEventHandler ;

	@Inject
	@Bundle({ CrudKeys.class, Disbursement.class })
	private ResourceBundle resourceBundle;

	@Inject
	@CreateModelEvent
	private Event<Disbursement> createModelEvent;

	@Inject
	Disbursement model ;

	@Inject
	@ModalEntityCreateDoneEvent
	private Event<Disbursement> modalCashOutCreateDoneEvent;

	@PostConstruct
	public void postConstruct(){          

		modalCashOutCreateView.bind(model);
		//		handle cancel action
		modalCashOutCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modalCashOutCreateView.closeDialog();

			}
		});
		//  handele create action
		modalCashOutCreateView.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PropertyReader.copy(new Disbursement(), model);
			}
		});
		//  handle save action
		modalCashOutCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<Disbursement>> violations = modalCashOutCreateView.getView().validate(model);
				if (violations.isEmpty())
				{
					cashOutCreateService.setModel(model).start();
				}
				else
				{
					Dialogs.create().title(resourceBundle.getString("Entity_create_error.title"))
					.nativeTitleBar().message("Error").showError();
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
		cashOutCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DisbursementProcessService s = (DisbursementProcessService) event.getSource();
				Disbursement cashOutCreateResult = s.getValue();
				event.consume();
				s.reset();
				modalCashOutCreateView.closeDialog();
				Dialogs.create().message("Cash Out maked whith sucess !").showInformation();
				modalCashOutCreateDoneEvent.fire(cashOutCreateResult);

			}
		});

		cashOutCreateService.setOnFailed(serviceCallFailedEventHandler);

		createModelEvent.fire(model);

	}


	public Disbursement getModel() {
		return model;
	}
	public void handleModalCashOutCreateEvent(@Observes @ModalEntityCreateRequestedEvent Disbursement cashOut){
		PropertyReader.copy(cashOut, model);
		modalCashOutCreateView.showDiaLog();
	}
}
