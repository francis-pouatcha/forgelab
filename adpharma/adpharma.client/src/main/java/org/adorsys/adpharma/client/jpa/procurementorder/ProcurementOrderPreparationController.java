package org.adorsys.adpharma.client.jpa.procurementorder;

import java.awt.Dialog;
import java.util.ArrayList;
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

import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ProcurementOrderPreparationController {

	@Inject
	private ProcurementOrderPreparationView view;

	@Inject
	private ProcurementOrderPreparationData data ;

	@Inject
	private ProcurementOrderPreparationService preparationService;

	@Inject
	private SupplierSearchService supplierSearchService;

	@Inject
	@EntityCreateDoneEvent
	private Event<ProcurementOrder> preparationCreateDoneEvent ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@PostConstruct
	public void postConstruct(){
		view.bind(data);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showExceptionInNewWindow(exception);

			}
		});
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				preparationService.setEntity(data).start();

			}
		});

		preparationService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderPreparationService s = (ProcurementOrderPreparationService) event.getSource();
				ProcurementOrder value = s.getValue();
				event.consume();
				s.reset();
				preparationCreateDoneEvent.fire(value);
				view.closeDialog();
			}
		});
		preparationService.setOnFailed(callFailedEventHandler);

		supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				SupplierSearchResult value = s.getValue();
				event.consume();
				s.reset();
				List<Supplier> resultList = value.getResultList();
				view.getSupplier().getItems().setAll(resultList);
			}
		});
		supplierSearchService.setOnFailed(callFailedEventHandler);

	}

	public void handleProcurementOrderPreparation(@Observes @ModalEntityCreateRequestedEvent ProcurementOrderPreparationData model){
		PropertyReader.copy(model, data);
		SupplierSearchInput searchInput = new SupplierSearchInput();
		searchInput.setMax(-1);
		supplierSearchService.setSearchInputs(searchInput).start();
		view.showDiaLog();

	}

}
