package org.adorsys.adpharma.client.jpa.salesorder;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class SalesOrderPrinter {

	@Inject
	private SalesOrderPrinterDataService soDataService;
	@Inject
	private ServiceCallFailedEventHandler invoiceDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog invoiceDataErrorMessageDialog;
	
	@Inject
	private SalesOrderItemsFetchDataService soItemDataService;
	@Inject
	private ServiceCallFailedEventHandler invoiceItemDataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog invoiceItemErrorMessageDialog;
		
	@Inject
	@Bundle({ CrudKeys.class, SalesOrderPrintTemplate.class, SalesOrder.class,
			SalesOrderItem.class, Company.class })
	private ResourceBundle resourceBundle;
	
	@Inject
	private Locale locale;
	
	public void handlePrintSalesOrderRequestedEvent(
			@Observes @PrintCustomerInvoiceRequestedEvent SalesOrderId salesOrderId) {
		soDataService.setSalesOrderId(salesOrderId.getId()).setCustomerName(salesOrderId.getCustomerName()).setIsProforma(salesOrderId.isProformat()).start();
	}

	public void handlePrintSalesOrderRequestedEvent(
			@Observes @PrintCustomerInvoiceRequestedEvent CustomerInvoice customerInvoice) {
		soDataService.setSalesOrderId(customerInvoice.getSalesOrder().getId()).start();
	}
	
	@PostConstruct
	public void postConstruct() {
		soDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderPrinterDataService s = (SalesOrderPrinterDataService) event
						.getSource();
	            SalesOrderPrinterData invoiceData = s.getValue();
	            event.consume();
	            s.reset();
	    		if(invoiceData==null) return;
	    		if(invoiceData.getSalesOrderItemSearchResult().getResultList().isEmpty()) return;
	    		String customerName = s.getCustomerName();
	    		if(StringUtils.isNotBlank(customerName)){
	    			invoiceData.getSalesOrder().getCustomer().setFullName(customerName);
	    		}
	    		
	    		SalesOrderPrintTemplate worker = new SalesOrderPrintTemplate(invoiceData, resourceBundle, locale);
	    		worker.addItems(invoiceData.getSalesOrderItemSearchResult().getResultList());
	    		soItemDataService.setSalesOrderPrintTemplateWorker(worker).setSalesOrderPrinterData(invoiceData).start();
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
		soDataService.setOnFailed(invoiceDataServiceCallFailedEventHandler);

		invoiceDataErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						invoiceDataErrorMessageDialog.closeDialog();
					}
				});
		
		soItemDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemsFetchDataService s = (SalesOrderItemsFetchDataService) event.getSource();
	            SalesOrderPrinterData soData = s.getValue();
	            SalesOrderPrintTemplate worker = s.getSalesOrderPrintTemplateWorker();
	            event.consume();
	            s.reset();
	    		if(soData==null) return;
	    		if(soData.getSalesOrderItemSearchResult().getResultList().isEmpty()) {
	    			worker.closeReport();
	    			String soNumber = soData.getSalesOrder().getSoNumber();
	    			File file = new File(soNumber+".pdf");
	    			if(file.exists())openFile(file);
	    		} else {
	    			worker.addItems(soData.getSalesOrderItemSearchResult().getResultList());
	    			soItemDataService.setSalesOrderPrintTemplateWorker(worker).setSalesOrderPrinterData(soData).start();
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
		soItemDataService.setOnFailed(invoiceItemDataServiceCallFailedEventHandler);
		invoiceItemErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						invoiceItemErrorMessageDialog.closeDialog();
					}
				});
	}

	private void openFile(File file){
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
