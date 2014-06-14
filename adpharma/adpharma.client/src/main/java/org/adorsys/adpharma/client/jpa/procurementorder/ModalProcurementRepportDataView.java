package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.PeriodicalDeliveryDataSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalProcurementRepportDataView extends ApplicationModal{

	private AnchorPane rootPane;

	private CalendarTextField beginDate;

	private CalendarTextField endDate;

	private ComboBox<Supplier> supplier;

	private CheckBox check;

	private Button saveButton;

	private Button resetButton;

	@Inject
	private Locale locale ;

	@Inject
	private CalendarTextFieldValidator calendarControlValidator;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class,Delivery.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		supplier = lazyviewBuilder.addComboBox("Delivery_supplier_description.title", "supplier", resourceBundle);
		beginDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_fromDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		endDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_toDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		check = lazyviewBuilder.addCheckBox("Entity_empty.text", "check", resourceBundle);
		check.setText(resourceBundle.getString("SalesOrder_repport_check_description.title"));

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
	}



	public void bind(PeriodicalDeliveryDataSearchInput model)
	{
		beginDate.calendarProperty().bindBidirectional(model.beginDateProperty());
		endDate.calendarProperty().bindBidirectional(model.endDateProperty());
		check.selectedProperty().bindBidirectional(model.checkProperty());
		supplier.valueProperty().bindBidirectional(model.supplierProperty());
	}

	public void addValidators()
	{
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<PeriodicalDeliveryDataSearchInput>> validate(PeriodicalDeliveryDataSearchInput model)
	{
		Set<ConstraintViolation<PeriodicalDeliveryDataSearchInput>> violations = new HashSet<ConstraintViolation<PeriodicalDeliveryDataSearchInput>>();
		violations.addAll(calendarControlValidator.validate(beginDate, PeriodicalDeliveryDataSearchInput.class, "beginDate", resourceBundle));
		violations.addAll(calendarControlValidator.validate(endDate, PeriodicalDeliveryDataSearchInput.class, "endDate", resourceBundle));
		return violations;
	}

	@Override
	public Pane getRootPane() {
		return rootPane;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public Locale getLocale() {
		return locale;
	}
	public ComboBox<Supplier> getSupplier(){
		return supplier ;
	}


}
