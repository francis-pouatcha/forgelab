package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.events.ProcurementOrderId;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReportPrinterDataService;
import org.adorsys.adpharma.client.jpa.print.PrintDialog;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class ProcurementOrderReportPrinter {

	@Inject
	private ProcurementOrderReportPrinterDataService dataService;
	@Inject
	private ServiceCallFailedEventHandler dataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog dataErrorMessageDialog;

	@Inject
	private ProcurementOrderReportItemFetchDataService itemDataService;
	@Inject
	private ServiceCallFailedEventHandler itemDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog itemErrorMessageDialog;

	@Inject
	@Bundle({ CrudKeys.class, ProcurementOrderReportPrintTemplate.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private PrintDialog printDialog;

	public void handlePrintRequestedEvent(
			@Observes @PrintRequestedEvent ProcurementOrderId procurementOrderId) {
		ProcurementOrder procurementOrder = new ProcurementOrder();
		procurementOrder.setId(procurementOrderId.getId());
		dataService.setProcuremnetOrder(procurementOrder).start();
	}

	@PostConstruct
	public void postConstruct() {
		dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderReportPrinterDataService s = (ProcurementOrderReportPrinterDataService) event.getSource();
				ProcurementOrderReportPrinterData procurementOrderData = s.getValue();
				event.consume();
				s.reset();
				if(procurementOrderData==null) return;
				if(procurementOrderData.getProcurementOrderItemSearchResult().getResultList().isEmpty()) return;
				ProcurementOrderReportPrintTemplate worker = new ProcurementOrderReportPrintTemplate(procurementOrderData, resourceBundle, locale);
				worker.addItems(procurementOrderData.getProcurementOrderItemSearchResult().getResultList());
				itemDataService.setProcurementOrderReportPrintTemplateWorker(worker).setProcurementOrderReportPrinterData(procurementOrderData).start();
			}
		});

		dataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				dataErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					dataErrorMessageDialog.getDetailText().setText(message);
				dataErrorMessageDialog.display();
			}
		});
		dataService.setOnFailed(dataServiceCallFailedEventHandler);

		dataErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dataErrorMessageDialog.closeDialog();
					}
				});

		itemDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				ProcurementOrderReportItemFetchDataService s = (ProcurementOrderReportItemFetchDataService) event.getSource();
				ProcurementOrderReportPrinterData procurementOrderData = s.getValue();
				ProcurementOrderReportPrintTemplate worker = s.getProcurementOrderReportPrintTemplateWorker();
				event.consume();
				s.reset();
				if(procurementOrderData==null) return;
				if(procurementOrderData.getProcurementOrderItemSearchResult().getResultList().isEmpty()) {
					//	    			worker.closeInvoice();
					List<VBox> pages = worker.getPages();
					printDialog.getPages().clear();
					printDialog.getPages().addAll(pages);
					printDialog.show();
					//	    			Stage dialog = new Stage();
					//	    			dialog.initModality(Modality.APPLICATION_MODAL);
					//	    			// Stage
					//	    			Scene scene = new Scene(pages.iterator().next());
					////	    			scene.getStylesheets().add("/styles/application.css");
					//	    			dialog.setScene(scene);
					//	    			dialog.setTitle(deliveryData.getDelivery().getDeliveryNumber());
					//	    			dialog.show();


					//	    			PrinterJob job = PrinterJob.createPrinterJob();
					//	    			job.showPrintDialog(dialog);
					////					JobSettings jobSettings = job.getJobSettings();
					////	    			@SuppressWarnings("unused")
					////	    			Printer printer = job.getPrinter();
					//	    			if (job != null) {
					//	    				boolean success =  false;
					//	    				for (VBox page : pages) {
					//	    					success = job.printPage(page);
					//	    					if(!success) break;
					//	    				}
					//	    				if (success) {
					//	    					job.endJob();
					//	    				}
					//	    			}

				} else {
					worker.addItems(procurementOrderData.getProcurementOrderItemSearchResult().getResultList());
					itemDataService.setProcurementOrderReportPrintTemplateWorker(worker).setProcurementOrderReportPrinterData(procurementOrderData).start();
				}
			}
		});

		itemDataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				itemErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					itemErrorMessageDialog.getDetailText().setText(message);
				itemErrorMessageDialog.display();
			}
		});
		itemDataService.setOnFailed(itemDataServiceCallFailedEventHandler);
		itemErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						itemErrorMessageDialog.closeDialog();
					}
				});
	}

}
