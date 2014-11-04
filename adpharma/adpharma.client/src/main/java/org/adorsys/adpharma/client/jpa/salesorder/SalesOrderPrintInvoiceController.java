package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.PrintCustomerInvoiceRequestedEvent;
import org.adorsys.adpharma.client.events.SalesOrderId;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;


@Singleton
public class SalesOrderPrintInvoiceController {

	@Inject
	private SalesOrderPrintInvoiceView view;
	
	@Inject
	private SalesOrderPrintInvoiceData model;
	
	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	@Inject
	@PrintCustomerInvoiceRequestedEvent
	private Event<SalesOrderId> printCustomerInvoiceRequestedEvent;
	
	
	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
			}
		});
		
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				view.closeDialog();
			}
		});
		
		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				SalesOrderId salesOrderId = model.getSalesOrderId();
				if(salesOrderId==null || salesOrderId.getId()==null) return;
				salesOrderId.setProformat(Boolean.FALSE);
				if(model.getInvoiceDate()!=null) {
					salesOrderId.setInvoiceDate(DateHelper.format(model.getInvoiceDate().getTime(), DateHelper.DATE_TIME_FORMAT));
				}
				if(model.getCustomerName()!=null) {
					salesOrderId.setCustomerName(model.getCustomerName());
				}
				printCustomerInvoiceRequestedEvent.fire(salesOrderId);
				view.closeDialog();
			}
		});
		
	}
	
	
	
	public void handleSalesOrderPrintInvoiceRequestEvent(@Observes @ModalEntityCreateRequestedEvent SalesOrderPrintInvoiceData data){
		PropertyReader.copy(data, model);
		view.showDiaLog();
	}
	
	
}
