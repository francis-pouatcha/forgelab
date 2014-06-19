package org.adorsys.adpharma.client.jpa.customer;

import java.math.BigDecimal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import org.adorsys.adpharma.client.jpa.payment.PaymentSearchInput;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchResult;
import org.adorsys.adpharma.client.jpa.payment.PaymentSearchService;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
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
	
	private CustomerSearchResult searchResult;


	@PostConstruct
	public void postConstruct(){
		
		view.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new CustomerSearchInput());
				int start = 0;
				int max = searchResult.getSearchInput().getMax();
				if (newValue != null)
				{
					start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
				}
				searchResult.getSearchInput().setStart(start);
				customerSearchService.setSearchInputs(searchResult.getSearchInput()).start();


			}
				});

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
			this.searchResult = customerSearchResult;
			view.getDataList().getItems().setAll(customerSearchResult.getResultList());
			int maxResult = customerSearchResult.getSearchInput() != null ? customerSearchResult.getSearchInput().getMax() : 5;
			int pageCount = PaginationUtils.computePageCount(customerSearchResult.getCount(), maxResult);
			view.getPagination().setPageCount(pageCount);
			int firstResult = customerSearchResult.getSearchInput() != null ? customerSearchResult.getSearchInput().getStart() : 0;
			int pageIndex = PaginationUtils.computePageIndex(firstResult, customerSearchResult.getCount(), maxResult);
			view.getPagination().setCurrentPageIndex(pageIndex);
			view.getDataList().getItems().setAll(searchResult.getResultList());
			if(!view.isDisplayed())
				view.showDiaLog();
		}
		
	}


	public ModalCustomerSearchView getView() {
		return view;
	}

	public void handleCustomerSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent CustomerSearchInput customerSearchInput){
		if(customerSearchInput!=null)
		customerSearchInput.setMax(30);
		customerSearchService.setSearchInputs(customerSearchInput).start();
	}



}
