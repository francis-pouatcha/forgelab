package org.adorsys.adpharma.client.jpa.payment;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.ComboBox;
import org.adorsys.adpharma.client.jpa.login.LoginRoleNamesForm;
import org.adorsys.adpharma.client.jpa.login.LoginRoleNamesSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;

public class PaymentCashierForm extends AbstractToOneAssociation<Payment, Login>
{

   private TextField loginName;

   private TextField email;

   private ComboBox<Gender> gender;

   @Inject
   @Bundle({ CrudKeys.class, Login.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(Gender.class)
   private ResourceBundle genderBundle;

   @Inject
   private GenderConverter genderConverter;

   @Inject
   private GenderListCellFatory genderListCellFatory;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      loginName = viewBuilder.addTextField("Login_loginName_description.title", "loginName", resourceBundle);
      email = viewBuilder.addTextField("Login_email_description.title", "email", resourceBundle);
      gender = viewBuilder.addComboBox("Login_gender_description.title", "gender", resourceBundle, Gender.values());

      ComboBoxInitializer.initialize(gender, genderConverter, genderListCellFatory, genderBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      loginName.textProperty().bindBidirectional(model.getCashier().loginNameProperty());
      email.textProperty().bindBidirectional(model.getCashier().emailProperty());
      gender.valueProperty().bindBidirectional(model.getCashier().genderProperty());
   }

   public TextField getLoginName()
   {
      return loginName;
   }

   public TextField getEmail()
   {
      return email;
   }

   public ComboBox<Gender> getGender()
   {
      return gender;
   }
}