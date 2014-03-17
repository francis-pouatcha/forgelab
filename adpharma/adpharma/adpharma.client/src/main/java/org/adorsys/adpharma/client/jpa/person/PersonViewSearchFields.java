package org.adorsys.adpharma.client.jpa.person;

import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.person.Person;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;

public class PersonViewSearchFields extends AbstractForm<Person>
{

   private TextField firstName;

   private TextField lastName;

   private TextField phoneNumber;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   private TextField email;

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

      gridRows = viewBuilder.toRows();
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
}
