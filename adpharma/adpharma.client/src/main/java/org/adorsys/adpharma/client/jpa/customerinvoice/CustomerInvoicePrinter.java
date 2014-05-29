package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class CustomerInvoicePrinter {

	@Inject
	private CustomerInvoicePrinterDataService invoiceDataService;
	@Inject
	private ServiceCallFailedEventHandler invoiceDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog invoiceDataErrorMessageDialog;
	
	@Inject
	private CustomerInvoiceItemFetchDataService invoiceItemDataService;
	@Inject
	private ServiceCallFailedEventHandler invoiceItemDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog invoiceItemErrorMessageDialog;
		
	@Inject
	@Bundle({ CrudKeys.class, CustomerInvoicePrintTemplate.class, CustomerInvoice.class,
			CustomerInvoiceItem.class, Company.class })
	private ResourceBundle resourceBundle;
	
	@Inject
	private Locale locale;
	
//	public void handlePrintCustomerInvoiceRequestedEvent(
//			@Observes @PrintCustomerInvoiceRequestedEvent SalesOrderId salesOrderId) {
//		CustomerInvoice customerInvoice = new CustomerInvoice();
//		CustomerInvoiceSalesOrder salesOrder = new CustomerInvoiceSalesOrder();
//		salesOrder.setId(salesOrderId.getId());
//		customerInvoice.setSalesOrder(salesOrder);
//		CustomerInvoiceSearchInput searchInputs = new CustomerInvoiceSearchInput();
//		searchInputs.setEntity(customerInvoice);
//		searchInputs.getFieldNames().add("salesOrder");
//		invoiceDataService.setSearchInputs(searchInputs).start();
//	}
//
//	public void handlePrintCustomerInvoiceRequestedEvent(
//			@Observes @PrintCustomerInvoiceRequestedEvent CustomerInvoice customerInvoice) {
//		CustomerInvoiceSearchInput searchInputs = new CustomerInvoiceSearchInput();
//		searchInputs.setEntity(customerInvoice);
//		searchInputs.getFieldNames().add("salesOrder");
//		invoiceDataService.setSearchInputs(searchInputs).start();
//	}
	
	@PostConstruct
	public void postConstruct() {
		invoiceDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoicePrinterDataService s = (CustomerInvoicePrinterDataService) event
						.getSource();
	            CustomerInvoicePrinterData invoiceData = s.getValue();
	            event.consume();
	            s.reset();
	    		if(invoiceData==null) return;
	    		if(invoiceData.getCustomerInvoiceItemSearchResult().getResultList().isEmpty()) return;
	    		CustomerInvoicePrintTemplate worker = new CustomerInvoicePrintTemplate(invoiceData, resourceBundle, locale);
	    		worker.addItems(invoiceData.getCustomerInvoiceItemSearchResult().getResultList());
	    		invoiceItemDataService.setCustomerInvoicePrintTemplateWorker(worker).setCustomerInvoicePrinterData(invoiceData).start();
			}
		});

		invoiceDataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				invoiceDataErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					invoiceDataErrorMessageDialog.getDetailText().setText(message);
				invoiceDataErrorMessageDialog.display();
			}
		});
		invoiceDataService.setOnFailed(invoiceDataServiceCallFailedEventHandler);

		invoiceDataErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						invoiceDataErrorMessageDialog.closeDialog();
					}
				});
		
		invoiceItemDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceItemFetchDataService s = (CustomerInvoiceItemFetchDataService) event.getSource();
	            CustomerInvoicePrinterData invoiceData = s.getValue();
	            CustomerInvoicePrintTemplate worker = s.getCustomerInvoicePrintTemplateWorker();
	            event.consume();
	            s.reset();
	    		if(invoiceData==null) return;
	    		if(invoiceData.getCustomerInvoiceItemSearchResult().getResultList().isEmpty()) {
	    			worker.closeInvoice();
	    			List<VBox> pages = worker.getPages();
	    			Stage dialog = new Stage();
	    			dialog.initModality(Modality.APPLICATION_MODAL);
	    			// Stage
	    			Scene scene = new Scene(pages.iterator().next());
//	    			scene.getStylesheets().add("/styles/application.css");
	    			dialog.setScene(scene);
	    			dialog.setTitle(invoiceData.getCustomerInvoice().getInvoiceNumber());
	    			dialog.show();

	    			
	    			PrinterJob job = PrinterJob.createPrinterJob();
	    			job.showPrintDialog(dialog);
//					JobSettings jobSettings = job.getJobSettings();
//	    			@SuppressWarnings("unused")
//	    			Printer printer = job.getPrinter();
	    			if (job != null) {
	    				boolean success =  false;
	    				for (VBox page : pages) {
	    					success = job.printPage(page);
	    					if(!success) break;
	    				}
	    				if (success) {
	    					job.endJob();
	    				}
	    			}
	    			
	    		} else {
	    			worker.addItems(invoiceData.getCustomerInvoiceItemSearchResult().getResultList());
	    			invoiceItemDataService.setCustomerInvoicePrintTemplateWorker(worker).setCustomerInvoicePrinterData(invoiceData).start();
	    		}
			}
		});

		invoiceItemDataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				invoiceItemErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					invoiceItemErrorMessageDialog.getDetailText().setText(message);
				invoiceItemErrorMessageDialog.display();
			}
		});
		invoiceItemDataService.setOnFailed(invoiceItemDataServiceCallFailedEventHandler);
		invoiceItemErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						invoiceItemErrorMessageDialog.closeDialog();
					}
				});
	}
}
