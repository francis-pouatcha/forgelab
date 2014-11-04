package org.adorsys.adpharma.client.jpa.salesorder;


import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.CalendarTextField;

@Singleton
public class SalesOrderPrintInvoiceView extends ApplicationModal{
	
	private AnchorPane rootPane;
	
	private TextField customerName;
	
	private CalendarTextField invoiceDate;
	
	private Button saveButton;
	
	private Button cancelButton;
	
	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Override
	public Pane getRootPane() {
		return rootPane;
	}
	
	@PostConstruct
	public void postConstruct(){
		buildView();
	}
	
	public void buildView() {
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		customerName =lazyviewBuilder.addTextField("SalesOrder_printInvoice_customerName_description.title", "customerName", resourceBundle);
		lazyviewBuilder.addSeparator();
		invoiceDate= lazyviewBuilder.addCalendarTextField("SalesOrder_printInvoice_invoiceDate_description.title", "invoiceDate", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefSize(500, 200);
		
	}
	
	public void bind(SalesOrderPrintInvoiceData model) {
		customerName.textProperty().bindBidirectional(model.customerNameProperty());
		invoiceDate.calendarProperty().bindBidirectional(model.invoiceDateProperty());
	}

	public TextField getCustomerName() {
		return customerName;
	}

	public void setCustomerName(TextField customerName) {
		this.customerName = customerName;
	}

	public CalendarTextField getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(CalendarTextField invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	
	
	

}
