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

import org.adorsys.adpharma.client.SecurityUtil;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
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

	@Inject
	private AgencySearchService agencySearchService;

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
				Set<ConstraintViolation<InvoiceByAgencyPrintInput>> violations = view.getView().validate(invoiceByAgencyPrintInput);
				if (violations.isEmpty())
				{
					if(view.getView().getGroupPerDays().isSelected())
						invoiceByAgencySearchService.setPerDay(Boolean.TRUE);
					invoiceByAgencySearchService.setSearchInputs(invoiceByAgencyPrintInput).start();
				}

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
				Agency agency = invoiceByAgencyPrintInput.getAgency();
				try {
					customerInvoiceListPrintTemplatePdf = new CustomerInvoiceListPrintTemplatePdf(securityUtil.getConnectedUser(),agency, resourceBundle, locale);
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
		view.getView().getAgency().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					agencySearchService.setSearchInputs(new AgencySearchInput()).start();
			}
		});

		agencySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				AgencySearchService s = (AgencySearchService) event.getSource();
				AgencySearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<Agency> resultList = cs.getResultList();
				resultList.add(0, new Agency());
				view.getView().getAgency().getItems().setAll(resultList);

			}
		});


		agencySearchService.setOnFailed(callFailedEventHandler);
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
