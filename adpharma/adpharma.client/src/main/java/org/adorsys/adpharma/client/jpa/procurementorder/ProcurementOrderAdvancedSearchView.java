package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ProcurementOrderAdvancedSearchView extends ApplicationModal {
	
	private AnchorPane rootPane;
	
	private CalendarTextField fromDate;
	
	private CalendarTextField toDate;
	
	private ComboBox<DocumentProcessingState> state;
	
	private ComboBox<Login> creatingUser;
	
	private ComboBox<Supplier> supplier;
	
	private TextField procurementOrderNumber;
	
	private Button saveButton;
	
	private Button cancelButton;
	
	@Inject
	@Bundle({CrudKeys.class, ProcurementOrder.class})
	private ResourceBundle resourceBundle;
	
	@Inject
	private Locale locale;
	
	
	
	@PostConstruct
	public void postConsruct() {
		LazyViewBuilder lazyViewBuilder = new LazyViewBuilder();
		fromDate= lazyViewBuilder.addCalendarTextField("ProcurementOrder_Advancedsearch_fromDate_description.title", "fromDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		toDate= lazyViewBuilder.addCalendarTextField("ProcurementOrder_Advancedsearch_toDate_description.title", "toDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		procurementOrderNumber= lazyViewBuilder.addTextField("ProcurementOrder_Advancedsearch_orderNumber_description.title", "procurementOrderNumber", resourceBundle);
		state= lazyViewBuilder.addComboBox("ProcurementOrder_Advancedsearch_status_description.title", "state", resourceBundle, DocumentProcessingState.valuesWithNull());
		creatingUser= lazyViewBuilder.addComboBox("ProcurementOrder_Advancedsearch_creatingUser_description.title", "creatingUser", resourceBundle);
		supplier= lazyViewBuilder.addComboBox("ProcurementOrder_Advancedsearch_supplier_description.title", "supplier", resourceBundle);
		fromDate.setPrefWidth(300d);
		toDate.setPrefWidth(300d);
		state.setPrefWidth(300d);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyViewBuilder.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefSize(500, 320);
	}
	
	
	public void bind(ProcurementOrderPreparationData model) {
		fromDate.calendarProperty().bindBidirectional(model.fromDateProperty());
		toDate.calendarProperty().bindBidirectional(model.toDateProperty());
		procurementOrderNumber.textProperty().bindBidirectional(model.procurementOrderNumberProperty());
		state.valueProperty().bindBidirectional(model.poStatusProperty());
		supplier.valueProperty().bindBidirectional(model.supplierProperty());
		creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
	}
	
	public void addValidators(){
		
		// No active Validator
	}
	
	
	
	public CalendarTextField getFromDate() {
		return fromDate;
	}
	
	public CalendarTextField getToDate() {
		return toDate;
	}
	
	public ComboBox<DocumentProcessingState> getState() {
		return state;
	}
	
	public ComboBox<Login> getCreatingUser() {
		return creatingUser;
	}
	
	public ComboBox<Supplier> getSupplier() {
		return supplier;
	}
	
	public TextField getProcurementOrderNumber() {
		return procurementOrderNumber;
	}
	
	public Button getSaveButton() {
		return saveButton;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}
	
	
	@Override
	public Pane getRootPane() {
		return rootPane;
				
	}

}
