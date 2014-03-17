package org.adorsys.adpharma.client.jpa.person;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.math.BigDecimal;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
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
import org.adorsys.adpharma.client.jpa.person.Person;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;

public class PersonView extends AbstractForm<Person>
{

   private TextField firstName;

   private TextField lastName;

   private TextField phoneNumber;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   private TextField email;

   private ComboBox<Gender> gender;

   private BigDecimalField discountRate;

   private CalendarTextField recordingDate;

   @Inject
   private PersonLoginForm personLoginForm;
   @Inject
   private PersonLoginSelection personLoginSelection;

   @Inject
   private PersonAgencyForm personAgencyForm;
   @Inject
   private PersonAgencySelection personAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, Person.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(Gender.class)
   private ResourceBundle genderBundle;

   @Inject
   private GenderConverter genderConverter;

   @Inject
   private GenderListCellFatory genderListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      firstName = viewBuilder.addTextField("Person_firstName_description.title", "firstName", resourceBundle);
      lastName = viewBuilder.addTextField("Person_lastName_description.title", "lastName", resourceBundle);
      phoneNumber = viewBuilder.addTextField("Person_phoneNumber_description.title", "phoneNumber", resourceBundle);
      street = viewBuilder.addTextField("Person_street_description.title", "street", resourceBundle);
      zipCode = viewBuilder.addTextField("Person_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Person_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Person_country_description.title", "country", resourceBundle);
      email = viewBuilder.addTextField("Person_email_description.title", "email", resourceBundle);
      gender = viewBuilder.addComboBox("Person_gender_description.title", "gender", resourceBundle, Gender.values());
      discountRate = viewBuilder.addBigDecimalField("Person_discountRate_description.title", "discountRate", resourceBundle, NumberType.PERCENTAGE, locale);
      recordingDate = viewBuilder.addCalendarTextField("Person_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("Person_login_description.title", resourceBundle);
      viewBuilder.addSubForm("Person_login_description.title", "login", resourceBundle, personLoginForm, ViewModel.WRITE_ONLY);
      viewBuilder.addSubForm("Person_login_description.title", "login", resourceBundle, personLoginSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Person_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("Person_agency_description.title", "agency", resourceBundle, personAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Person_agency_description.title", "agency", resourceBundle, personAgencySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(gender, genderConverter, genderListCellFatory, genderBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      lastName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Person>(textInputControlValidator, lastName, Person.class, "lastName", resourceBundle));
   }

   public Set<ConstraintViolation<Person>> validate(Person model)
   {
      Set<ConstraintViolation<Person>> violations = new HashSet<ConstraintViolation<Person>>();
      violations.addAll(textInputControlValidator.validate(lastName, Person.class, "lastName", resourceBundle));
      return violations;
   }

   public void bind(Person model)
   {
      firstName.textProperty().bindBidirectional(model.firstNameProperty());
      lastName.textProperty().bindBidirectional(model.lastNameProperty());
      phoneNumber.textProperty().bindBidirectional(model.phoneNumberProperty());
      street.textProperty().bindBidirectional(model.streetProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      city.textProperty().bindBidirectional(model.cityProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
      email.textProperty().bindBidirectional(model.emailProperty());
      gender.valueProperty().bindBidirectional(model.genderProperty());
      discountRate.numberProperty().bindBidirectional(model.discountRateProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      personLoginForm.bind(model);
      personLoginSelection.bind(model);
      personAgencyForm.bind(model);
      personAgencySelection.bind(model);
   }

   public TextField getFirstName()
   {
      return firstName;
   }

   public TextField getLastName()
   {
      return lastName;
   }

   public TextField getPhoneNumber()
   {
      return phoneNumber;
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

   public TextField getEmail()
   {
      return email;
   }

   public ComboBox<Gender> getGender()
   {
      return gender;
   }

   public BigDecimalField getDiscountRate()
   {
      return discountRate;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public PersonLoginForm getPersonLoginForm()
   {
      return personLoginForm;
   }

   public PersonLoginSelection getPersonLoginSelection()
   {
      return personLoginSelection;
   }

   public PersonAgencyForm getPersonAgencyForm()
   {
      return personAgencyForm;
   }

   public PersonAgencySelection getPersonAgencySelection()
   {
      return personAgencySelection;
   }
}
