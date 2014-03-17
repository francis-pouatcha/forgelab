package org.adorsys.adpharma.client.jpa.customer;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerType;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerTypeConverter;
import org.adorsys.adpharma.client.jpa.customertype.CustomerTypeListCellFatory;

public class CustomerViewSearchFields extends AbstractForm<Customer>
{

   private TextField firstName;

   private TextField lastName;

   private TextField fullName;

   private TextField landLinePhone;

   private TextField mobile;

   private TextField fax;

   private TextField email;

   private CheckBox creditAuthorized;

   private CheckBox discountAuthorized;

   private TextField serialNumber;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(Gender.class)
   private ResourceBundle genderBundle;

   @Inject
   private GenderConverter genderConverter;

   @Inject
   private GenderListCellFatory genderListCellFatory;
   @Inject
   @Bundle(CustomerType.class)
   private ResourceBundle customerTypeBundle;

   @Inject
   private CustomerTypeConverter customerTypeConverter;

   @Inject
   private CustomerTypeListCellFatory customerTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      firstName = viewBuilder.addTextField("Customer_firstName_description.title", "firstName", resourceBundle);
      lastName = viewBuilder.addTextField("Customer_lastName_description.title", "lastName", resourceBundle);
      fullName = viewBuilder.addTextField("Customer_fullName_description.title", "fullName", resourceBundle);
      landLinePhone = viewBuilder.addTextField("Customer_landLinePhone_description.title", "landLinePhone", resourceBundle);
      mobile = viewBuilder.addTextField("Customer_mobile_description.title", "mobile", resourceBundle);
      fax = viewBuilder.addTextField("Customer_fax_description.title", "fax", resourceBundle);
      email = viewBuilder.addTextField("Customer_email_description.title", "email", resourceBundle);
      creditAuthorized = viewBuilder.addCheckBox("Customer_creditAuthorized_description.title", "creditAuthorized", resourceBundle);
      discountAuthorized = viewBuilder.addCheckBox("Customer_discountAuthorized_description.title", "discountAuthorized", resourceBundle);
      serialNumber = viewBuilder.addTextField("Customer_serialNumber_description.title", "serialNumber", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Customer model)
   {
      firstName.textProperty().bindBidirectional(model.firstNameProperty());
      lastName.textProperty().bindBidirectional(model.lastNameProperty());
      fullName.textProperty().bindBidirectional(model.fullNameProperty());
      landLinePhone.textProperty().bindBidirectional(model.landLinePhoneProperty());
      mobile.textProperty().bindBidirectional(model.mobileProperty());
      fax.textProperty().bindBidirectional(model.faxProperty());
      email.textProperty().bindBidirectional(model.emailProperty());
      creditAuthorized.textProperty().bindBidirectional(model.creditAuthorizedProperty(), new BooleanStringConverter());
      discountAuthorized.textProperty().bindBidirectional(model.discountAuthorizedProperty(), new BooleanStringConverter());
      serialNumber.textProperty().bindBidirectional(model.serialNumberProperty());

   }

   public TextField getFirstName()
   {
      return firstName;
   }

   public TextField getLastName()
   {
      return lastName;
   }

   public TextField getFullName()
   {
      return fullName;
   }

   public TextField getLandLinePhone()
   {
      return landLinePhone;
   }

   public TextField getMobile()
   {
      return mobile;
   }

   public TextField getFax()
   {
      return fax;
   }

   public TextField getEmail()
   {
      return email;
   }

   public CheckBox getCreditAuthorized()
   {
      return creditAuthorized;
   }

   public CheckBox getDiscountAuthorized()
   {
      return discountAuthorized;
   }

   public TextField getSerialNumber()
   {
      return serialNumber;
   }
}
