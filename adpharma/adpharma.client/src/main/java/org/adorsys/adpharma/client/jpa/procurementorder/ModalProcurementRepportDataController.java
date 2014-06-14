package org.adorsys.adpharma.client.jpa.procurementorder;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.delivery.PeriodicalDeliveryDataSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderReportPrintTemplatePDF;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

import com.lowagie.text.DocumentException;

@Singleton
public class ModalProcurementRepportDataController {

	@Inject
	private ModalProcurementRepportDataView view ;

	@Inject
	private PeriodicalDeliveryDataSearchInput model ;

	@Inject
	private SupplierSearchService supplierSearchService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private DeliveryItemSearchService deliveryItemSearchService ;
	
	@Inject
	private SecurityUtil securityUtil;

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
				Set<ConstraintViolation<PeriodicalDeliveryDataSearchInput>> validate = view.validate(model);
				if(validate.isEmpty()){
					deliveryItemSearchService.setData(model).start();
				}

			}
		});
		
		deliveryItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemSearchService source = (DeliveryItemSearchService) event.getSource();
				List<DeliveryItem> resultList = source.getValue().getResultList();
				source.reset();
				event.consume();
				

				try {
					Login login = securityUtil.getConnectedUser();
					LoginAgency agency = securityUtil.getAgency();
					ProcurementReportPrintTemplatePDF worker = new ProcurementReportPrintTemplatePDF(login,agency,model);
					worker.addItems(resultList);
					worker.closeDocument();
					File file = new File(worker.getFileName());
					openFile(file);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(resultList);
				
			}
		});
		deliveryItemSearchService.setOnFailed(callFailedEventHandler);
	}


	public void handleSalesRepportSearchDataRequestEvent(@Observes @EntitySearchRequestedEvent PeriodicalDeliveryDataSearchInput data){
		PropertyReader.copy(data, model);
		view.showDiaLog();
	}
	
	private void openFile(File file){
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}


}
