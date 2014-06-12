package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.delivery.PeriodicalDeliveryDataSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalProcurementRepportDataController {

	@Inject
	private ModalProcurementRepportDataView view ;
	
	@Inject
	private PeriodicalDataSearchInput model ;
	
	@Inject
	private SupplierSearchService supplierSearchService ;
	
	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
				
			}
		});
		view.getSupplier().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					SupplierSearchInput searchInput = new SupplierSearchInput();
					searchInput.setMax(-1);
					supplierSearchService.setSearchInputs(searchInput ).start();;
				}
				
			}
		});
		supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService source = (SupplierSearchService) event.getSource();
				SupplierSearchResult supplierSearchResult = source.getValue();
				event.consume();
				source.reset();
				
				view.getSupplier().getItems().setAll(supplierSearchResult.getResultList());
			}
		});
		supplierSearchService.setOnFailed(callFailedEventHandler);
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});
	}
	
	
	public void handleSalesRepportSearchDataRequestEvent(@Observes @EntitySearchRequestedEvent PeriodicalDeliveryDataSearchInput data){
		PropertyReader.copy(data, model);
		view.showDiaLog();
	}

}
