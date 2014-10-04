package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalPrescriptionBookRepportDataView extends ApplicationModal {
	
	private AnchorPane rootPane;
	
	private CalendarTextField beginDate;

	private CalendarTextField endDate;
	
	private Button saveButton;

	private Button resetButton;

	@Inject
	private Locale locale ;

	@Inject
	private CalendarTextFieldValidator calendarControlValidator;

	@Inject
	@Bundle({ CrudKeys.class, PrescriptionBook.class,PrescriptionBook.class })
	private ResourceBundle resourceBundle;
	
	
	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		beginDate = lazyviewBuilder.addCalendarTextField("PrescriptionBook_repport_fromDate_description.title", "beginDate", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		endDate = lazyviewBuilder.addCalendarTextField("PrescriptionBook_toDate_description.title", "endDate", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(500);
	}	
	
	
	public void bind(PeriodicalPrescriptionBookDataSearchInput model)
	{
		beginDate.calendarProperty().bindBidirectional(model.beginDateProperty());
		endDate.calendarProperty().bindBidirectional(model.endDateProperty());
	}
	
	public void addValidators()
	{
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<PeriodicalPrescriptionBookDataSearchInput>> validate(PeriodicalPrescriptionBookDataSearchInput model)
	{
		Set<ConstraintViolation<PeriodicalPrescriptionBookDataSearchInput>> violations = new HashSet<ConstraintViolation<PeriodicalPrescriptionBookDataSearchInput>>();
		violations.addAll(calendarControlValidator.validate(beginDate, PeriodicalPrescriptionBookDataSearchInput.class, "beginDate", resourceBundle));
		violations.addAll(calendarControlValidator.validate(endDate, PeriodicalPrescriptionBookDataSearchInput.class, "endDate", resourceBundle));
		return violations;
	}
	
	
	

	@Override
	public Pane getRootPane() {
          return rootPane;		
	}
	
	public CalendarTextField getBeginDate() {
		return beginDate;
	}
	
	public CalendarTextField getEndDate() {
		return endDate;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public Button getSaveButton() {
		return saveButton;
	}
	
	public Button getResetButton() {
		return resetButton;
	}
	
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	

}
