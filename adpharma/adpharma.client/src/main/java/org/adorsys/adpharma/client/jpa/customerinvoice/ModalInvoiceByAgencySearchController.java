package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSalesKeyResetService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

import com.lowagie.text.DocumentException;

@Singleton
public class ModalInvoiceByAgencySearchController  {
	@Inject
	private ModalInvoiceByAgencySearchView view ;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	private LoginSalesKeyResetService loginSalesKeyResetService  ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;


	@Inject
	private InvoiceByAgencyPrintInput invoiceByAgencyPrintInput ;

	@Inject
	private CustomerInvoiceByAgencySearchService invoiceByAgencySearchService ;


	private CustomerInvoiceListPrintTemplatePdf customerInvoiceListPrintTemplatePdf ;

	@Inject
	@Bundle({CustomerInvoicePrintTemplate.class,CustomerInvoice.class})
	private ResourceBundle resourceBundle ;

	@Inject
	private Locale locale ;


	@PostConstruct
	public void ppostContruct(){
		view.bind(invoiceByAgencyPrintInput);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

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
				invoiceByAgencySearchService.setSearchInputs(invoiceByAgencyPrintInput).start();

			}
		});
		invoiceByAgencySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceByAgencySearchService s = (CustomerInvoiceByAgencySearchService) event.getSource();
				CustomerInvoiceSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoice> resultList = cs.getResultList();
				Login connectedUser = securityUtil.getConnectedUser();
				try {
					customerInvoiceListPrintTemplatePdf = new CustomerInvoiceListPrintTemplatePdf(connectedUser,connectedUser.getAgency(), resourceBundle, locale);
					customerInvoiceListPrintTemplatePdf.addItems(resultList);
					customerInvoiceListPrintTemplatePdf.closeDocument();
					File file = new File(customerInvoiceListPrintTemplatePdf.getFileName());
					if(file.exists()) {
						openFile(file);
					} 
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		invoiceByAgencySearchService.setOnFailed(callFailedEventHandler);

	}

	public void handleUserInfoRequestEvent(@Observes @EntitySearchRequestedEvent InvoiceByAgencyPrintInput searchInput){
		PropertyReader.copy(searchInput, invoiceByAgencyPrintInput);
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
