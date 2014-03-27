package org.adorsys.adpharma.client.jpa.customer;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchResult;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchService;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchService;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCreateService;
import org.adorsys.adpharma.client.jpa.insurrance.ModalInsurranceCreateView;
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
public class ModalCustomerCreateController {

	@Inject
	ModalCustomerCreateView createView;

	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;  

	@Inject 
	CustomerCreateService createService;
	
	@Inject
	private EmployerSearchService employerSearchService;
	
	@Inject
	private CustomerCategorySearchService categorySearchService ;

	@Inject   
	ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	@ModalEntityCreateDoneEvent 
	private Event<Customer> modalCustomerCreateDoneEvent ;

   @Inject
	private Customer customer ;

	@PostConstruct
	public void postConstruct(){
 		createView.bind(customer);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);				
			}
		});

		//		cancel
		createView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				createView.closeDialog();

			}
		});
		//		reset
		createView.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PropertyReader.copy(new Customer(), customer);
			}
		});
		//		save
		createView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<Customer>> contraintViolations = createView.getView().validate(customer);
				if(contraintViolations.isEmpty()){
					createService.setModel(customer).start();
				}else {
					Dialogs.create().title(resourceBundle.getString("Entity_create_error.title"))
					.nativeTitleBar().message(resourceBundle.getString("Entity_click_to_see_error")).showError();
				}

			}
		});

		//		add validators
		createView.getView().addValidators();

		createService.setOnFailed(callFailedEventHandler);
		createService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerCreateService source = (CustomerCreateService) event.getSource();
				Customer customer2 = source.getValue();
				event.consume();
				source.reset();
			   	modalCustomerCreateDoneEvent.fire(customer2);
				createView.closeDialog();

			}
		});
		
	}

	private void handleCustomerCreateRequestEvent(@Observes @ModalEntityCreateRequestedEvent Customer model){
		PropertyReader.copy(model, customer);
		createView.showDiaLog();
	}
}
