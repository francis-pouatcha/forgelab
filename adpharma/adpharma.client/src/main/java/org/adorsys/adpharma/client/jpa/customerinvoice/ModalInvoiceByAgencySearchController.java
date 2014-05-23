package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.SecurityUtil;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReportPrintTemplate;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReportPrintTemplatePDF;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSalesKeyResetService;
import org.adorsys.adpharma.client.jpa.login.ModalUserInfoView;
import org.adorsys.adpharma.client.jpa.login.UserInfoView;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.login.UserInfoRequestEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.controlsfx.dialog.Dialogs;

import de.jensd.fx.fontawesome.AwesomeIcon;

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
	private AgencySearchService agencySearchService;

	@Inject
	private CustomerInvoiceSearchService customerInvoiceSearchService ;

	private CustomerInvoiceListPrintTemplatePdf customerInvoiceListPrintTemplatePdf ;

	@Inject
	@Bundle({DeliveryReportPrintTemplate.class,CustomerInvoice.class})
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
				customerInvoiceSearchService.setSearchInputs(new CustomerInvoiceSearchInput()).start();

			}
		});
		customerInvoiceSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event.getSource();
				CustomerInvoiceSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<CustomerInvoice> resultList = cs.getResultList();

				customerInvoiceListPrintTemplatePdf = new CustomerInvoiceListPrintTemplatePdf(securityUtil.getConnectedUser(), resourceBundle, locale);
				customerInvoiceListPrintTemplatePdf.addItems(resultList);
				customerInvoiceListPrintTemplatePdf.closeReport();
				File file = new File(customerInvoiceListPrintTemplatePdf.getFileName());
				if(file.exists()) {
					openFile(file);
				} 
			}
		});


		customerInvoiceSearchService.setOnFailed(callFailedEventHandler);
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
