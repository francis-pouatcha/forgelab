package org.adorsys.adpharma.client.jpa.customerinvoice;

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
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.events.PaymentId;
import org.adorsys.adpharma.client.events.PrintPaymentReceiptRequestedEvent;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class ReceiptPrinter {

	@Inject
	private ReceiptPrinterDataService receiptPrinterDataService;
	@Inject
	private ServiceCallFailedEventHandler receiptPrinterDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog receiptPrinterDataServiceErrorMessageDialog;
	
	@Inject
	private CustomerInvoiceItemFetchDataService invoiceItemDataService;
	@Inject
	private ServiceCallFailedEventHandler invoiceItemDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog invoiceItemErrorMessageDialog;

	
	
	@Inject
	@Bundle({ CrudKeys.class, ReceiptPrintTemplate.class, CustomerInvoice.class,
			CustomerInvoiceItem.class, Company.class, PaymentMode.class })
	private ResourceBundle resourceBundle;
	
	@Inject
	private Locale locale;
	
	public void handlePrintPaymentReceiptRequestedEvent(
			@Observes @PrintPaymentReceiptRequestedEvent PaymentId paymentId) {
		receiptPrinterDataService.setPaymentId(paymentId).start();
	}

	@PostConstruct
	public void postConstruct() {
		receiptPrinterDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				ReceiptPrinterDataService s = (ReceiptPrinterDataService) event.getSource();
	            ReceiptPrinterData receiptPrinterData = s.getValue();
	            event.consume();
	            s.reset();
	    		if(receiptPrinterData==null) return;
	    		if(receiptPrinterData.getPayment()==null) return;
	    		ReceiptPrintTemplate worker = new ReceiptPrintTemplate(receiptPrinterData, resourceBundle, locale);
	    		worker.startPage();
	    		CustomerInvoicePrinterData customerInvoicePrinterData = worker.nextInvoice();
	    		if(customerInvoicePrinterData==null) {
	    			worker.closePayment();
	    		} else {
					worker.printInvoiceHeader(customerInvoicePrinterData);
					worker.addItems(customerInvoicePrinterData.getCustomerInvoiceItemSearchResult().getResultList());
					invoiceItemDataService.setReceiptPrintTemplateWorker(worker).setCustomerInvoicePrinterData(customerInvoicePrinterData).start();
	    		}
			}
		});

		receiptPrinterDataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				receiptPrinterDataServiceErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					receiptPrinterDataServiceErrorMessageDialog.getDetailText().setText(message);
				receiptPrinterDataServiceErrorMessageDialog.display();
			}
		});
		receiptPrinterDataService.setOnFailed(receiptPrinterDataServiceCallFailedEventHandler);

		receiptPrinterDataServiceErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						receiptPrinterDataServiceErrorMessageDialog.closeDialog();
					}
				});
		
		invoiceItemDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceItemFetchDataService s = (CustomerInvoiceItemFetchDataService) event.getSource();
	            CustomerInvoicePrinterData invoiceData = s.getValue();
	            ReceiptPrintTemplate worker = s.getReceiptPrintTemplateWorker();
	            event.consume();
	            s.reset();
	    		if(invoiceData==null) return;
	    		if(invoiceData.getCustomerInvoiceItemSearchResult().getResultList().isEmpty()) {
	    			CustomerInvoicePrinterData customerInvoicePrinterData = worker.nextInvoice();
		    		if(customerInvoicePrinterData==null) {
		    			worker.closePayment();
		    			VBox page = worker.getPage();
		    			Stage dialog = new Stage();
		    			dialog.initModality(Modality.APPLICATION_MODAL);
		    			// Stage
		    			Scene scene = new Scene(page);
//		    			scene.getStylesheets().add("/styles/application.css");
		    			dialog.setScene(scene);
		    			dialog.setTitle(invoiceData.getCustomerInvoice().getInvoiceNumber());
		    			dialog.show();

		    			
		    			PrinterJob job = PrinterJob.createPrinterJob();
		    			job.showPrintDialog(dialog);
//						JobSettings jobSettings = job.getJobSettings();
//		    			@SuppressWarnings("unused")
//		    			Printer printer = job.getPrinter();
		    			if (job != null) {
		    				boolean success =  job.printPage(page);
		    				if (success) {
		    					job.endJob();
		    				}
		    			}
		    		} else {
						worker.printInvoiceHeader(customerInvoicePrinterData);
						worker.addItems(customerInvoicePrinterData.getCustomerInvoiceItemSearchResult().getResultList());
						invoiceItemDataService.setReceiptPrintTemplateWorker(worker).setCustomerInvoicePrinterData(customerInvoicePrinterData).start();
		    		}
	    		} else {
	    			// same invoice
	    			worker.addItems(invoiceData.getCustomerInvoiceItemSearchResult().getResultList());
	    			invoiceItemDataService.setReceiptPrintTemplateWorker(worker).setCustomerInvoicePrinterData(invoiceData).start();
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
