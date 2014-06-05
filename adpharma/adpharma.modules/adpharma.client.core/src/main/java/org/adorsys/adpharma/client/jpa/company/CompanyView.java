package org.adorsys.adpharma.client.jpa.company;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
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
import org.adorsys.adpharma.client.jpa.company.Company;

public class CompanyView extends AbstractForm<Company>
{

   private TextField displayName;

   private TextField phone;

   private TextField fax;

   private TextField siteManager;

   private TextField email;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   private TextField siteInternet;

   private TextField mobile;

   private TextField registerNumber;

   private CalendarTextField recordingDate;

   @Inject
   @Bundle({ CrudKeys.class, Company.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      displayName = viewBuilder.addTextField("Company_displayName_description.title", "displayName", resourceBundle);
      phone = viewBuilder.addTextField("Company_phone_description.title", "phone", resourceBundle);
      fax = viewBuilder.addTextField("Company_fax_description.title", "fax", resourceBundle);
      siteManager = viewBuilder.addTextField("Company_siteManager_description.title", "siteManager", resourceBundle);
      email = viewBuilder.addTextField("Company_email_description.title", "email", resourceBundle);
      street = viewBuilder.addTextField("Company_street_description.title", "street", resourceBundle);
      zipCode = viewBuilder.addTextField("Company_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Company_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Company_country_description.title", "country", resourceBundle);
      siteInternet = viewBuilder.addTextField("Company_siteInternet_description.title", "siteInternet", resourceBundle);
      mobile = viewBuilder.addTextField("Company_mobile_description.title", "mobile", resourceBundle);
      registerNumber = viewBuilder.addTextField("Company_registerNumber_description.title", "registerNumber", resourceBundle);
      recordingDate = viewBuilder.addCalendarTextField("Company_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      displayName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Company>(textInputControlValidator, displayName, Company.class, "displayName", resourceBundle));
   }

   public Set<ConstraintViolation<Company>> validate(Company model)
   {
      Set<ConstraintViolation<Company>> violations = new HashSet<ConstraintViolation<Company>>();
      violations.addAll(textInputControlValidator.validate(displayName, Company.class, "displayName", resourceBundle));
      return violations;
   }

   public void bind(Company model)
   {
      displayName.textProperty().bindBidirectional(model.displayNameProperty());
      phone.textProperty().bindBidirectional(model.phoneProperty());
      fax.textProperty().bindBidirectional(model.faxProperty());
      siteManager.textProperty().bindBidirectional(model.siteManagerProperty());
      email.textProperty().bindBidirectional(model.emailProperty());
      street.textProperty().bindBidirectional(model.streetProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      city.textProperty().bindBidirectional(model.cityProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
      siteInternet.textProperty().bindBidirectional(model.siteInternetProperty());
      mobile.textProperty().bindBidirectional(model.mobileProperty());
      registerNumber.textProperty().bindBidirectional(model.registerNumberProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
   }

   public TextField getDisplayName()
   {
      return displayName;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextField getSiteManager()
   {
      return siteManager;
   }

   public TextField getEmail()
   {
      return email;
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

   public TextField getSiteInternet()
   {
      return siteInternet;
   }

   public TextField getMobile()
   {
      return mobile;
   }

   public TextField getRegisterNumber()
   {
      return registerNumber;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }
}
