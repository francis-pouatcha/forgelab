package org.adorsys.adpharma.client.jpa.customer;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import javax.validation.ConstraintViolation;

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

public class CustomerView extends AbstractForm<Customer>
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

   private ComboBox<Gender> gender;

   private ComboBox<CustomerType> customerType;

   private BigDecimalField totalCreditLine;

   private BigDecimalField totalDebt;

   private CalendarTextField birthDate;

   @Inject
   private CustomerEmployerForm customerEmployerForm;
   @Inject
   private CustomerEmployerSelection customerEmployerSelection;

   @Inject
   private CustomerCustomerCategoryForm customerCustomerCategoryForm;
   @Inject
   private CustomerCustomerCategorySelection customerCustomerCategorySelection;

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

   @Inject
   private TextInputControlValidator textInputControlValidator;

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
      gender = viewBuilder.addComboBox("Customer_gender_description.title", "gender", resourceBundle, Gender.values());
      customerType = viewBuilder.addComboBox("Customer_customerType_description.title", "customerType", resourceBundle, CustomerType.values());
      totalCreditLine = viewBuilder.addBigDecimalField("Customer_totalCreditLine_description.title", "totalCreditLine", resourceBundle, NumberType.INTEGER, locale);
      totalDebt = viewBuilder.addBigDecimalField("Customer_totalDebt_description.title", "totalDebt", resourceBundle, NumberType.CURRENCY, locale);
      birthDate = viewBuilder.addCalendarTextField("Customer_birthDate_description.title", "birthDate", resourceBundle, "dd-MM-yyyy", locale);
//      viewBuilder.addTitlePane("Customer_employer_description.title", resourceBundle);
//      viewBuilder.addSubForm("Customer_employer_description.title", "employer", resourceBundle, customerEmployerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Customer_employer_description.title", "employer", resourceBundle, customerEmployerSelection, ViewModel.READ_ONLY);
//      viewBuilder.addTitlePane("Customer_customerCategory_description.title", resourceBundle);
//      viewBuilder.addSubForm("Customer_customerCategory_description.title", "customerCategory", resourceBundle, customerCustomerCategoryForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Customer_customerCategory_description.title", "customerCategory", resourceBundle, customerCustomerCategorySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(gender, genderConverter, genderListCellFatory, genderBundle);
      ComboBoxInitializer.initialize(customerType, customerTypeConverter, customerTypeListCellFatory, customerTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      lastName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Customer>(textInputControlValidator, lastName, Customer.class, "lastName", resourceBundle));
      fullName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Customer>(textInputControlValidator, fullName, Customer.class, "fullName", resourceBundle));
   }

   public Set<ConstraintViolation<Customer>> validate(Customer model)
   {
      Set<ConstraintViolation<Customer>> violations = new HashSet<ConstraintViolation<Customer>>();
      violations.addAll(textInputControlValidator.validate(lastName, Customer.class, "lastName", resourceBundle));
      violations.addAll(textInputControlValidator.validate(fullName, Customer.class, "fullName", resourceBundle));
      return violations;
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
      gender.valueProperty().bindBidirectional(model.genderProperty());
      customerType.valueProperty().bindBidirectional(model.customerTypeProperty());
      totalCreditLine.numberProperty().bindBidirectional(model.totalCreditLineProperty());
      totalDebt.numberProperty().bindBidirectional(model.totalDebtProperty());
      birthDate.calendarProperty().bindBidirectional(model.birthDateProperty());
      customerEmployerForm.bind(model);
      customerEmployerSelection.bind(model);
      customerCustomerCategoryForm.bind(model);
      customerCustomerCategorySelection.bind(model);
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

   public ComboBox<Gender> getGender()
   {
      return gender;
   }

   public ComboBox<CustomerType> getCustomerType()
   {
      return customerType;
   }

   public BigDecimalField getTotalCreditLine()
   {
      return totalCreditLine;
   }

   public BigDecimalField getTotalDebt()
   {
      return totalDebt;
   }

   public CalendarTextField getBirthDate()
   {
      return birthDate;
   }

   public CustomerEmployerForm getCustomerEmployerForm()
   {
      return customerEmployerForm;
   }

   public CustomerEmployerSelection getCustomerEmployerSelection()
   {
      return customerEmployerSelection;
   }

   public CustomerCustomerCategoryForm getCustomerCustomerCategoryForm()
   {
      return customerCustomerCategoryForm;
   }

   public CustomerCustomerCategorySelection getCustomerCustomerCategorySelection()
   {
      return customerCustomerCategorySelection;
   }
}
