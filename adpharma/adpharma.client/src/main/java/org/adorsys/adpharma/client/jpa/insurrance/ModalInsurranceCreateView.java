package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalInsurranceCreateView extends ApplicationModal {

	private BigDecimalField coverageRate;

	private CalendarTextField beginDate;

	private CalendarTextField endDate;

	private ComboBox<InsurranceCustomer> insurranceCustomerSelection;

	private ComboBox<InsurranceInsurer> insurranceInsurerSelection;

	@Inject
	@Bundle({ CrudKeys.class, Insurrance.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private CalendarTextFieldValidator calendarTextFieldValidator;

	@Inject
	private BigDecimalFieldValidator bigDecimalFieldValidator ;

	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

	private AnchorPane rootPane;

	private Button saveButton;

	private Button resetButton;

	private Button cancelButton;

	@Override
	public AnchorPane getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		coverageRate = lazyviewBuilder.addBigDecimalField("Insurrance_coverageRate_description.title", "coverageRate", resourceBundle, NumberType.INTEGER, locale);
		insurranceCustomerSelection = lazyviewBuilder.addComboBox("Insurrance_customer_description.title", "customer", resourceBundle);
		insurranceInsurerSelection = lazyviewBuilder.addComboBox("Insurrance_insurer_description.title", "insurer", resourceBundle);

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(400);
	}
	public void addValidators()
	   {
	      coverageRate.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<Insurrance>(bigDecimalFieldValidator, coverageRate, Insurrance.class, "endDate", resourceBundle));

	      // no active validator
	      // no active validator
	   }

	   public Set<ConstraintViolation<Insurrance>> validate(Insurrance model)
	   {
		   
	      Set<ConstraintViolation<Insurrance>> violations = new HashSet<ConstraintViolation<Insurrance>>();
	      violations.addAll(bigDecimalFieldValidator.validate(coverageRate, Insurrance.class, "coverageRate", resourceBundle));
	      violations.addAll(toOneAggreggationFieldValidator.validate(insurranceCustomerSelection, model.getCustomer(), Insurrance.class, "customer", resourceBundle));
	      violations.addAll(toOneAggreggationFieldValidator.validate(insurranceInsurerSelection, model.getInsurer(), Insurrance.class, "insurer", resourceBundle));
	      return violations;
	   }
	
	

	public void bind(Insurrance model)
	{
		coverageRate.numberProperty().bindBidirectional(model.coverageRateProperty());
		insurranceCustomerSelection.valueProperty().bindBidirectional(model.customerProperty());
		insurranceInsurerSelection.valueProperty().bindBidirectional(model.insurerProperty());

	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public BigDecimalField getCoverageRate() {
		return coverageRate;
	}

	public void setCoverageRate(BigDecimalField coverageRate) {
		this.coverageRate = coverageRate;
	}

	public CalendarTextField getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(CalendarTextField beginDate) {
		this.beginDate = beginDate;
	}

	public CalendarTextField getEndDate() {
		return endDate;
	}

	public void setEndDate(CalendarTextField endDate) {
		this.endDate = endDate;
	}

	public ComboBox<InsurranceCustomer> getInsurranceCustomerSelection() {
		return insurranceCustomerSelection;
	}

	public void setInsurranceCustomerSelection(
			ComboBox<InsurranceCustomer> insurranceCustomerSelection) {
		this.insurranceCustomerSelection = insurranceCustomerSelection;
	}

	public ComboBox<InsurranceInsurer> getInsurranceInsurerSelection() {
		return insurranceInsurerSelection;
	}

	public void setInsurranceInsurerSelection(
			ComboBox<InsurranceInsurer> insurranceInsurerSelection) {
		this.insurranceInsurerSelection = insurranceInsurerSelection;
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

	public CalendarTextFieldValidator getCalendarTextFieldValidator() {
		return calendarTextFieldValidator;
	}

	public void setCalendarTextFieldValidator(
			CalendarTextFieldValidator calendarTextFieldValidator) {
		this.calendarTextFieldValidator = calendarTextFieldValidator;
	}

	public BigDecimalFieldValidator getBigDecimalFieldValidator() {
		return bigDecimalFieldValidator;
	}

	public void setBigDecimalFieldValidator(
			BigDecimalFieldValidator bigDecimalFieldValidator) {
		this.bigDecimalFieldValidator = bigDecimalFieldValidator;
	}

	public ToOneAggreggationFieldValidator getToOneAggreggationFieldValidator() {
		return toOneAggreggationFieldValidator;
	}

	public void setToOneAggreggationFieldValidator(
			ToOneAggreggationFieldValidator toOneAggreggationFieldValidator) {
		this.toOneAggreggationFieldValidator = toOneAggreggationFieldValidator;
	}

	public void setRootPane(AnchorPane rootPane) {
		this.rootPane = rootPane;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public void setResetButton(Button resetButton) {
		this.resetButton = resetButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}



}
