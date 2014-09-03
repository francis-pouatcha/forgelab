package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SalesOrderAdvenceSearchController {

	@Inject
	private SalesorderAdvenceSearchView view ;

	@Inject
	private SalesorderAdvenceSearchService advenceSearchService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private LoginSearchService loginSearchService ;

	@Inject
	@EntitySearchDoneEvent
	private Event<SalesOrderSearchResult> searchResultEvent ;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showprogressEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressEvent ;

	@Inject
	private SalesOrderAdvenceSearchData data ;


	@PostConstruct
	public void postConstruct(){
		view.getSaveButton().disableProperty().bind(advenceSearchService.runningProperty());
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();
			}
		});

		view.getSaller().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					LoginSearchInput searchInput = new LoginSearchInput();
					searchInput.setMax(-1);
					loginSearchService.setSearchInputs(searchInput).start();
				}

			}
		});
		loginSearchService.setOnFailed(callFailedEventHandler);
		loginSearchService.setOnSucceeded(new  EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSearchService source = (LoginSearchService) event.getSource();
				Login login = new Login();
				login.setLoginName("TOUS LES VENDEURS");
				List<Login> resultList = source.getValue().getResultList();
				resultList.sort(new Comparator<Login>() {

					@Override
					public int compare(Login o1, Login o2) {
						// TODO Auto-generated method stub
						return o1.getLoginName().compareTo(o2.getLoginName());
					}
				});
				resultList.add(0,login);
				view.getSaller().getItems().setAll(resultList);
			}
		});
		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				readSearchInPut();
				advenceSearchService.setSearchInputs(data).start();
				showprogressEvent.fire(new Object());

			}
		});
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		advenceSearchService.setOnFailed(callFailedEventHandler);
		advenceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesorderAdvenceSearchService source = (SalesorderAdvenceSearchService) event.getSource();
				SalesOrderSearchResult value = source.getValue();
				value.getSearchInput().setStart(0);
				value.getSearchInput().setMax(200);
				searchResultEvent.fire(value);
				event.consume();
				source.reset();
				hideProgressEvent.fire(new Object());
				view.closeDialog();
			}
		});
	}


	public void handleAdvenceSearchRequestEvent(@Observes @EntitySearchRequestedEvent SalesOrderAdvenceSearchData model){
		PropertyReader.copy(model, data);
		view.showDiaLog();
	}

	public void readSearchInPut(){
		if(view.getFromDate()!=null)
			data.setFromDate(DateHelper.localDateToDate(view.getFromDate().getValue()));
		if(view.getToDate()!=null)
			data.setToDate(DateHelper.localDateToDate(view.getToDate().getValue()));
		if(StringUtils.isNotBlank(view.getArticleName().getText()))
			data.setArticleName(view.getArticleName().getText());
		if(view.getSaller().getValue()!=null &&view.getSaller().getValue().getId()!=null)
			data.setSaller(view.getSaller().getValue());
		if(view.getState().getValue()!=null)
			data.setSate(view.getState().getValue());
		data.setOnlyCrediSales(view.getOnlyCrediSales().isSelected());
	}
}
