package org.adorsys.adpharma.client.jpa.procurementorder;

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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;


@Singleton
public class ProcurementOrderAdvancedSearchController {
	
@Inject
private ProcurementOrderAdvancedSearchView advancedSearchView;

@Inject
private ServiceCallFailedEventHandler callFailedEventHandler ;

@Inject
private LoginSearchService loginSearchService ;

@Inject
private SupplierSearchService supplierSearchService;

@Inject
@ShowProgressBarRequestEvent
private Event<Object> showprogressEvent ;

@Inject
@HideProgressBarRequestEvent
private Event<Object> hideProgressEvent ;

@Inject
private ProcurementOrderPreparationData data;


@PostConstruct
public void postConstruct() {
	
	// Save action
	advancedSearchView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
			
			
		}
	});
	
	// Cancel action
	advancedSearchView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
		   advancedSearchView.closeDialog();   
		}
	});
	
	advancedSearchView.getSupplier().armedProperty().addListener(new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> arg0,
				Boolean oldValue, Boolean newValue) {
			if(newValue) {
			SupplierSearchInput supplierSearchInput= new SupplierSearchInput();
			supplierSearchInput.setMax(-1);
			supplierSearchService.setSearchInputs(supplierSearchInput).start();
			}
		}
	});
	
	supplierSearchService.setOnFailed(callFailedEventHandler);
	supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		@Override
		public void handle(WorkerStateEvent event) {
		 SupplierSearchService source=(SupplierSearchService) event.getSource();
		 SupplierSearchResult value = source.getValue();
		 event.consume();
		 source.reset();
		 List<Supplier> resultList = value.getResultList();
		 advancedSearchView.getSupplier().getItems().setAll(resultList);
		} 
	});
	
	advancedSearchView.getCreatingUser().armedProperty().addListener(new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> arg0,
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
					return o1.getLoginName().compareTo(o2.getLoginName());
				}
			});
			resultList.add(0,login);
			advancedSearchView.getCreatingUser().getItems().setAll(resultList);
		}
	});
	
	callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
		@Override
		protected void showError(Throwable exception) {
			Dialogs.create().showException(exception);
		}
	});
	
}

// Show procurement avancedSearchView
public void handleAdvenceSearchRequestEvent(@Observes @EntitySearchRequestedEvent ProcurementOrderPreparationData model){
	PropertyReader.copy(model, data);
	advancedSearchView.showDiaLog();
}



}
