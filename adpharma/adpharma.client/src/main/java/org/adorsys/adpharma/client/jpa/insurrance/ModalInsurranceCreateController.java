package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;
@Singleton
public class ModalInsurranceCreateController {

	@Inject
	ModalInsurranceCreateView createView;

	@Inject 
	InsurranceCreateService inssurancecreateService;

	@Inject   
	ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	@ModalEntityCreateDoneEvent 
	private Event<Insurrance> modalInsuranceCreateDoneEvent ;

	@Inject
	@CreateModelEvent
	private Event<Insurrance> createModelEvent;

	@Inject
	@Bundle({CrudKeys.class,Insurrance.class})
	private ResourceBundle resourceBundle;  

	@Inject
	private Insurrance insurrance ;
	
	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<CustomerSearchInput> modalCustomerSearchEvent;

	@PostConstruct
	public void postConstruct(){

		createView.bind(insurrance);
		
//		createView.getInsurranceInsurerSelection().o.addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable,
//					Boolean oldValue, Boolean newValue) {
//				if(newValue)
//					modalCustomerSearchEvent.fire(new CustomerSearchInput());
//				
//				
//			}
//		});
		
		createView.getInsurranceInsurerSelection().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				modalCustomerSearchEvent.fire(new CustomerSearchInput());
				
			}
		});

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
				InsurranceCustomer customer =new InsurranceCustomer();
				PropertyReader.copy(insurrance.getCustomer(), customer);// protect customer field
				PropertyReader.copy(new Insurrance(), insurrance);
				insurrance.setCustomer(customer);
			}
		});
		//		save
		createView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<Insurrance>> contraintViolations = createView.validate(insurrance);
				if(contraintViolations.isEmpty()){
					inssurancecreateService.setModel(insurrance).start();
				}else {
					Dialogs.create().title(resourceBundle.getString("Entity_create_error.title"))
					.nativeTitleBar().message(resourceBundle.getString("Entity_click_to_see_error")).showError();
				}

			}
		});

		//		add validators
		createView.addValidators();

		inssurancecreateService.setOnFailed(callFailedEventHandler);
		inssurancecreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InsurranceCreateService source = (InsurranceCreateService) event.getSource();
				Insurrance createdInssurance = source.getValue();
				event.consume();
				source.reset();
				createView.closeDialog();
				modalInsuranceCreateDoneEvent.fire(createdInssurance);

			}
		});

		createModelEvent.fire(insurrance);


	}

	private void handleInsuranceCreateRequestEvent(@Observes @ModalEntityCreateRequestedEvent Insurrance model){
		PropertyReader.copy(model, insurrance);
		createView.showDiaLog();
	}
	
	private void handleInsurrerSearchDoneRequestEvent(@Observes @ModalEntitySearchDoneEvent Customer customer ){
		if(customer!=null){
			createView.getInsurranceInsurerSelection().setValue(new InsurranceInsurer(customer));
		}
	}
}
