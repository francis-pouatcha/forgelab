package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.Printer;

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
import org.apache.commons.io.IOUtils;
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
	    		ReceiptPrintTemplate worker = new ReceiptPrintTemplatePDF(receiptPrinterData, resourceBundle, locale);
	    		worker.startPage();
	    		CustomerInvoicePrinterData customerInvoicePrinterData = worker.nextInvoice();
	    		if(customerInvoicePrinterData==null) {
	    			worker.closePayment();
	    		} else {
	    			if(receiptPrinterData.isUnDiscountReciept()){
	    				CustomerInvoice invoice = customerInvoicePrinterData.getCustomerInvoice();
	    				BigDecimal amountDiscount = invoice.getAmountDiscount()!=null?invoice.getAmountDiscount():BigDecimal.ZERO;
	    				invoice.setAmountDiscount(BigDecimal.ZERO); // invoice whithout discount 
	    				BigDecimal difference = receiptPrinterData.getPayment().getDifference();
	    				receiptPrinterData.getPayment().setDifference(difference.subtract(amountDiscount));
	    			}
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
		    			byte[] data =  (byte[]) worker.getPage();
//						PrintService[] printServices = PrintServiceLookup
//								.lookupPrintServices(DocFlavor.INPUT_STREAM.PDF, null);
//						PrintService printService = null;
//						if (printServices != null && printServices.length>=1) {
//							for (PrintService ps : printServices) {
//								if(StringUtils.containsIgnoreCase(ps.getName(), worker.getReceiptPrinterName())){
//									printService=ps;
//									break;
//								}
//							}
//						}
//						if(printService==null){
							ReceiptPrinterData receiptPrinterData = worker.getReceiptPrinterData();
							String paymentNumber = receiptPrinterData.getPayment().getPaymentNumber();
							String fileName = paymentNumber+".pdf";
							try {
								FileOutputStream fos = new FileOutputStream(fileName);
								IOUtils.write(data, fos);
								IOUtils.closeQuietly(fos);
								ReceiptPrintMode receiptPrintMode = worker.getReceiptPrintMode();
								switch (receiptPrintMode) {
								case print:
									Desktop.getDesktop().print(new File(fileName));
									break;
								case open:
									Desktop.getDesktop().open(new File(fileName));
									break;
								default:
									break;
								}
							} catch (IOException e) {
								throw new IllegalStateException(e);
							} finally{
//								FileUtils.deleteQuietly(new File(fileName));
							}
							
//						} else {
//							DocPrintJob printJob = printService.createPrintJob();
//							Doc doc = new SimpleDoc(new ByteArrayInputStream(data), DocFlavor.INPUT_STREAM.PDF,
//									null);
//							try {
//								printJob.print(doc, null);
//							} catch (PrintException e) {
//								throw new IllegalStateException(e);
//							}
//						}

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
