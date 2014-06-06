package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeConverter;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeListCellFatory;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

public class DebtStatementPreparationView extends ApplicationModal {

	private AnchorPane anchorPane;

	@FXML
	private VBox rootNode ;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	@FXML
	private GridPane entityForm ;

	@FXML
	private ComboBox<ProcmtOrderTriggerMode> mode;


	@FXML
	private ComboBox<Customer> insurrance;

	private CalendarTextField fromDate;

	private CalendarTextField toDate;

	@Inject
	@Bundle({ CrudKeys.class, DebtStatement.class })
	private ResourceBundle resourceBundle;

	@Inject
	@Bundle(ProcmtOrderTriggerMode.class)
	private ResourceBundle procmtOrderTriggerModeBundle;

	@Inject
	private ProcmtOrderTriggerModeConverter procmtOrderTriggerModeConverter;

	@Inject
	private ProcmtOrderTriggerModeListCellFatory procmtOrderTriggerModeListCellFatory;

	@Inject
	private FXMLLoader fxmlLoader;

	@Inject
	private Locale locale;


	@PostConstruct
	public void postConstruct(){
		FXMLLoaderUtils.load(fxmlLoader, this);
		anchorPane = new AnchorPane(rootNode);
		buildForm();
		ComboBoxInitializer.initialize(mode, procmtOrderTriggerModeConverter, procmtOrderTriggerModeListCellFatory, procmtOrderTriggerModeBundle);


	}
	@Override
	public Pane getRootPane() {
		return anchorPane;
	}

	public void bind(DebtStatementPreparationData model){
		mode.valueProperty().bindBidirectional(model.procmtOrderTriggerModeProperty());
		fromDate.calendarProperty().bindBidirectional(model.fromDateProperty());
		toDate.calendarProperty().bindBidirectional(model.toDateProperty());
		insurrance.valueProperty().bindBidirectional(model.insurranceProperty());

	}

	public void buildForm(){
		mode = ViewBuilderUtils.newComboBox("ProcurementOrder_procmtOrderTriggerMode_description.title", "procmtOrderTriggerMode", resourceBundle, ProcmtOrderTriggerMode.values(),false);
		mode.setPrefHeight(30);
		mode.setPrefWidth(400d);

		insurrance = ViewBuilderUtils.newComboBox("ProcurementOrder_supplier_description.title", "supplier",false);
		insurrance.setPrefHeight(30);
		insurrance.setPrefWidth(400d);

		fromDate = ViewBuilderUtils.newCalendarTextField( "fromDate", "dd-MM-yyyy HH:mm", locale,false);
		fromDate.setPrefHeight(30);

		toDate = ViewBuilderUtils.newCalendarTextField( "toDate", "dd-MM-yyyy HH:mm", locale,false);
		toDate.setPrefHeight(30);

		entityForm.addColumn(1,insurrance, mode,fromDate,toDate);

	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public ComboBox<ProcmtOrderTriggerMode> getMode() {
		return mode;
	}
	public ComboBox<Customer> getInsurrance() {
		return insurrance;
	}
	public CalendarTextField getFromDate() {
		return fromDate;
	}

	public CalendarTextField getToDate() {
		return toDate;
	}

	public Button getCancelButton() {
		return cancelButton;
	}
	public Button getSaveButton() {
		return saveButton;
	}

}
