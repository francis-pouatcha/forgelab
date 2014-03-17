package org.adorsys.adpharma.client.jpa.agency;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.company.Company;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javafx.crud.extensions.ViewModel;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public class AgencyView extends AbstractForm<Agency>
{

   private TextField agencyNumber;

   private TextField name;

   private TextArea description;

   private CheckBox active;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   private TextField phone;

   private TextField fax;

   private TextArea ticketMessage;

   private TextArea invoiceMessage;

   private CalendarTextField recordingDate;

   @Inject
   private AgencyCompanyForm agencyCompanyForm;
   @Inject
   private AgencyCompanySelection agencyCompanySelection;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      agencyNumber = viewBuilder.addTextField("Agency_agencyNumber_description.title", "agencyNumber", resourceBundle);
      name = viewBuilder.addTextField("Agency_name_description.title", "name", resourceBundle);
      description = viewBuilder.addTextArea("Agency_description_description.title", "description", resourceBundle);
      active = viewBuilder.addCheckBox("Agency_active_description.title", "active", resourceBundle);
      street = viewBuilder.addTextField("Agency_street_description.title", "street", resourceBundle);
      zipCode = viewBuilder.addTextField("Agency_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Agency_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Agency_country_description.title", "country", resourceBundle);
      phone = viewBuilder.addTextField("Agency_phone_description.title", "phone", resourceBundle);
      fax = viewBuilder.addTextField("Agency_fax_description.title", "fax", resourceBundle);
      ticketMessage = viewBuilder.addTextArea("Agency_ticketMessage_description.title", "ticketMessage", resourceBundle);
      invoiceMessage = viewBuilder.addTextArea("Agency_invoiceMessage_description.title", "invoiceMessage", resourceBundle);
      recordingDate = viewBuilder.addCalendarTextField("Agency_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("Agency_company_description.title", resourceBundle);
      viewBuilder.addSubForm("Agency_company_description.title", "company", resourceBundle, agencyCompanyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Agency_company_description.title", "company", resourceBundle, agencyCompanySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      description.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Agency>(textInputControlValidator, description, Agency.class, "description", resourceBundle));
      ticketMessage.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Agency>(textInputControlValidator, ticketMessage, Agency.class, "ticketMessage", resourceBundle));
      invoiceMessage.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Agency>(textInputControlValidator, invoiceMessage, Agency.class, "invoiceMessage", resourceBundle));
   }

   public Set<ConstraintViolation<Agency>> validate(Agency model)
   {
      Set<ConstraintViolation<Agency>> violations = new HashSet<ConstraintViolation<Agency>>();
      violations.addAll(textInputControlValidator.validate(description, Agency.class, "description", resourceBundle));
      violations.addAll(textInputControlValidator.validate(ticketMessage, Agency.class, "ticketMessage", resourceBundle));
      violations.addAll(textInputControlValidator.validate(invoiceMessage, Agency.class, "invoiceMessage", resourceBundle));
      return violations;
   }

   public void bind(Agency model)
   {
      agencyNumber.textProperty().bindBidirectional(model.agencyNumberProperty());
      name.textProperty().bindBidirectional(model.nameProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      street.textProperty().bindBidirectional(model.streetProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      city.textProperty().bindBidirectional(model.cityProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
      phone.textProperty().bindBidirectional(model.phoneProperty());
      fax.textProperty().bindBidirectional(model.faxProperty());
      ticketMessage.textProperty().bindBidirectional(model.ticketMessageProperty());
      invoiceMessage.textProperty().bindBidirectional(model.invoiceMessageProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      agencyCompanyForm.bind(model);
      agencyCompanySelection.bind(model);
   }

   public TextField getAgencyNumber()
   {
      return agencyNumber;
   }

   public TextField getName()
   {
      return name;
   }

   public TextArea getDescription()
   {
      return description;
   }

   public CheckBox getActive()
   {
      return active;
   }

   public TextField getStreet()
   {
      return street;
   }

   public TextField getZipCode()
   {
      return zipCode;
   }

   public TextField getCity()
   {
      return city;
   }

   public TextField getCountry()
   {
      return country;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextArea getTicketMessage()
   {
      return ticketMessage;
   }

   public TextArea getInvoiceMessage()
   {
      return invoiceMessage;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public AgencyCompanyForm getAgencyCompanyForm()
   {
      return agencyCompanyForm;
   }

   public AgencyCompanySelection getAgencyCompanySelection()
   {
      return agencyCompanySelection;
   }
}
