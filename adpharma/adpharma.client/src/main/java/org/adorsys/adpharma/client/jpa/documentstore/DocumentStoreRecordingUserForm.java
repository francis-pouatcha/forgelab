package org.adorsys.adpharma.client.jpa.documentstore;

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
import org.adorsys.adpharma.client.jpa.login.LoginAgencyForm;
import org.adorsys.adpharma.client.jpa.login.LoginAgencySelection;

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

public class DocumentStoreRecordingUserForm extends AbstractToOneAssociation<DocumentStore, Login>
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

   public void bind(DocumentStore model)
   {
      loginName.textProperty().bindBidirectional(model.getRecordingUser().loginNameProperty());
      email.textProperty().bindBidirectional(model.getRecordingUser().emailProperty());
      gender.valueProperty().bindBidirectional(model.getRecordingUser().genderProperty());
   }

   public void update(DocumentStoreRecordingUser data)
   {
      loginName.textProperty().set(data.loginNameProperty().get());
      email.textProperty().set(data.emailProperty().get());
      gender.valueProperty().set(data.genderProperty().get());
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
