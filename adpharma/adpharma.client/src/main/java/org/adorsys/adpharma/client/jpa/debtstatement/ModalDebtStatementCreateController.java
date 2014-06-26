package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderAdvenceSearchData;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesorderAdvenceSearchService;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalDebtStatementCreateController {

	@Inject
	private ModalDebtStatementCreateView view ;

	@Inject
	private DebtStatementCreateService debtStatementCreateService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private InsurranceSearchService insurranceSearchService ;

	@Inject
	@EntityCreateDoneEvent
	private Event<DebtStatement> createDoneEvent ;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showprogressEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressEvent ;

	@Inject
	private DebtStatementProcessingData data ;


	@PostConstruct
	public void postConstruct(){
		view.getSaveButton().disableProperty().bind(debtStatementCreateService.runningProperty());
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getCustomer().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					InsurranceSearchInput searchInput = new InsurranceSearchInput();
					searchInput.setMax(-1);
					insurranceSearchService.setSearchInputs(searchInput).start();
				}

			}
		});
		insurranceSearchService.setOnFailed(callFailedEventHandler);
		insurranceSearchService.setOnSucceeded(new  EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InsurranceSearchService source = (InsurranceSearchService) event.getSource();
				List<Insurrance> resultList = source.getValue().getResultList();
				List<Customer> result = new ArrayList<Customer>();
				resultList.sort(new Comparator<Insurrance>() {

					@Override
					public int compare(Insurrance o1, Insurrance o2) {
						// TODO Auto-generated method stub
						return o1.getInsurer().getFullName().compareTo(o2.getInsurer().getFullName());
					}
				});
				for (Insurrance inssurance : resultList) {
					Customer customer = new Customer();
					PropertyReader.copy(inssurance.getInsurer(), customer);
					if(!result.contains(customer))
						result.add(customer);
				}
				view.getCustomer().getItems().setAll(result);
			}
		});
		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				readSearchInPut();
				debtStatementCreateService.setData(data).start();
				showprogressEvent.fire(new Object());

			}
		});
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		debtStatementCreateService.setOnFailed(callFailedEventHandler);
		debtStatementCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DebtStatementCreateService source = (DebtStatementCreateService) event.getSource();
				DebtStatement value = source.getValue();
				createDoneEvent.fire(value);
				event.consume();
				source.reset();
				hideProgressEvent.fire(new Object());
				view.closeDialog();

			}
		});
	}


	public void handleAdvenceSearchRequestEvent(@Observes @EntityCreateRequestedEvent DebtStatementProcessingData model){
		PropertyReader.copy(model, data);
		view.showDiaLog();
	}

	public void readSearchInPut(){
		if(view.getFromDate()!=null)
			data.setFromDate(DateHelper.localDateToDate(view.getFromDate().getValue()));
		if(view.getToDate()!=null)
			data.setToDate(DateHelper.localDateToDate(view.getToDate().getValue()));
		if(view.getCustomer().getValue()!=null &&view.getCustomer().getValue().getId()!=null)
			data.setCustomer(view.getCustomer().getValue());
	}
}
