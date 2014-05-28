package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InvoiceByAgencySearchView extends AbstractForm<InvoiceByAgencyPrintInput>{
	private CalendarTextField fromDate;
	private CalendarTextField toDate;
	private ComboBox<Agency> agency;
	private CheckBox groupPerDays;

	@Inject
	@Bundle({ CrudKeys.class, CustomerInvoicePrintTemplate.class ,CustomerInvoice.class})
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private CalendarTextFieldValidator calendarTextFieldValidator ;

	@Inject
	private ToOneAggreggationFieldValidator aggreggationFieldValidator ;




	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		fromDate = viewBuilder.addCalendarTextField("CustomerInvoice_fromdate_description.title", "fromDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		toDate = viewBuilder.addCalendarTextField("CustomerInvoice_todate_description.title", "fromDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		agency = viewBuilder.addComboBox("CustomerInvoice_agency_description.title", "agency", resourceBundle);
        groupPerDays =viewBuilder.addCheckBox("Entity_empty.text", "groupPerDays", resourceBundle);
		groupPerDays.setText(resourceBundle.getString("CustomerInvoice_perDays_description.title"));
        gridRows = viewBuilder.toRows();
	}

	public void addValidators()
	{
		//		loginName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, loginName, Login.class, "loginName", resourceBundle));
		//		fullName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, fullName, Login.class, "fullName", resourceBundle));
	}

	public Set<ConstraintViolation<InvoiceByAgencyPrintInput>> validate(InvoiceByAgencyPrintInput model)
	{
		Set<ConstraintViolation<InvoiceByAgencyPrintInput>> violations = new HashSet<ConstraintViolation<InvoiceByAgencyPrintInput>>();
		violations.addAll(calendarTextFieldValidator.validate(fromDate, InvoiceByAgencyPrintInput.class, "fromDate", resourceBundle));
		violations.addAll(calendarTextFieldValidator.validate(toDate, InvoiceByAgencyPrintInput.class, "toDate", resourceBundle));
		violations.addAll(aggreggationFieldValidator.validate(agency, model.getAgency(), InvoiceByAgencyPrintInput.class, "agency", resourceBundle));

		return violations;
	}
	@Override
	public void bind(InvoiceByAgencyPrintInput model)
	{
		agency.valueProperty().bindBidirectional(model.agencyProperty());
		fromDate.calendarProperty().bindBidirectional(model.fromDateProperty());
		toDate.calendarProperty().bindBidirectional(model.toDateProperty());
	}

	public CalendarTextField getFromDate() {
		return fromDate;
	}

	public CalendarTextField getToDate() {
		return toDate;
	}

	public ComboBox<Agency> getAgency() {
		return agency;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public Locale getLocale() {
		return locale;
	}

	public CheckBox getGroupPerDays() {
		return groupPerDays;
	}

	public void setGroupPerDays(CheckBox groupPerDays) {
		this.groupPerDays = groupPerDays;
	}



}
