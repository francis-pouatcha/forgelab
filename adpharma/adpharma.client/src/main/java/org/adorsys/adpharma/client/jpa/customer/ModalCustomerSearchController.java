package org.adorsys.adpharma.client.jpa.customer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class ModalCustomerSearchController  {

	@Inject
	ModalCustomerSearchView view;

	@Inject 
	private CustomerSearchService customerSearchService;

	@Inject
	private ServiceCallFailedEventHandler articleSearchServiceCallFailedEventHandler;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<CustomerSearchInput> modalCustomerSearchEvent;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<Customer> modalCustomerSearchDoneEvent;

	@Inject
	Customer customer;


	@PostConstruct
	public void postConstruct(){
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();
			}
		});
		
		view.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(
					ObservableValue<? extends Customer> observable,
					Customer oldValue, Customer newValue) {
				if(newValue!=null){
					modalCustomerSearchDoneEvent.fire(newValue);
					view.closeDialog();
					
				}
					
				
			}
		});
		view.getCustomerName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String customerName = view.getCustomerName().getText();
					if(StringUtils.isBlank(customerName)) return;
					Customer entity = new Customer();
					entity.setFullName(customerName);
					CustomerSearchInput csi = new CustomerSearchInput();
					csi.setEntity(entity);
					csi.setMax(100);
					csi.getFieldNames().add("fullName");
					modalCustomerSearchEvent.fire(csi);
				}
			}
		});

		customerSearchService.setOnFailed(articleSearchServiceCallFailedEventHandler);
		customerSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerSearchService s = (CustomerSearchService) event.getSource();
				CustomerSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				handleCustomerSearchResult(searchResult);
			}



		});
	}

	public void handleCustomerSearchResult(
			CustomerSearchResult customerSearchResult) {
		if(customerSearchResult.getResultList().isEmpty()) return;
		if(customerSearchResult.getResultList().size()==1){
			Customer customerSearch = customerSearchResult.getResultList().iterator().next();
			PropertyReader.copy(new Customer(), customer);
			view.closeDialog();
			modalCustomerSearchDoneEvent.fire(customerSearch);
		}else {
			view.getDataList().getItems().setAll(customerSearchResult.getResultList());
			view.showDiaLog();
		}
	}


	public ModalCustomerSearchView getView() {
		return view;
	}

	public void handleCustomerSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent CustomerSearchInput customerSearchInput){
		customerSearchService.setSearchInputs(customerSearchInput).start();
	}



}
