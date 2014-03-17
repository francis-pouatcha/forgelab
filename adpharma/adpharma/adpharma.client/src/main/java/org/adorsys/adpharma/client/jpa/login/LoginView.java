package org.adorsys.adpharma.client.jpa.login;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.rolename.RoleName;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
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
import org.adorsys.adpharma.client.jpa.login.Login;

public class LoginView extends AbstractForm<Login>
{

   private TextField loginName;

   private TextField email;

   private TextField fullName;

   private TextField password;

   private CheckBox disableLogin;

   private CheckBox accountLocked;

   private TextField saleKey;

   private CalendarTextField credentialExpiration;

   private CalendarTextField accountExpiration;

   private CalendarTextField recordingDate;

   @Inject
   private LoginRoleNamesForm loginRoleNamesForm;
   @Inject
   private LoginRoleNamesSelection loginRoleNamesSelection;

   @Inject
   @Bundle({ CrudKeys.class, Login.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      loginName = viewBuilder.addTextField("Login_loginName_description.title", "loginName", resourceBundle);
      email = viewBuilder.addTextField("Login_email_description.title", "email", resourceBundle);
      fullName = viewBuilder.addTextField("Login_fullName_description.title", "fullName", resourceBundle);
      password = viewBuilder.addTextField("Login_password_description.title", "password", resourceBundle);
      disableLogin = viewBuilder.addCheckBox("Login_disableLogin_description.title", "disableLogin", resourceBundle);
      accountLocked = viewBuilder.addCheckBox("Login_accountLocked_description.title", "accountLocked", resourceBundle);
      saleKey = viewBuilder.addTextField("Login_saleKey_description.title", "saleKey", resourceBundle);
      credentialExpiration = viewBuilder.addCalendarTextField("Login_credentialExpiration_description.title", "credentialExpiration", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      accountExpiration = viewBuilder.addCalendarTextField("Login_accountExpiration_description.title", "accountExpiration", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      recordingDate = viewBuilder.addCalendarTextField("Login_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("Login_roleNames_description.title", resourceBundle);
      viewBuilder.addSubForm("Login_roleNames_description.title", "roleNames", resourceBundle, loginRoleNamesForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("Login_roleNames_description.title", "roleNames", resourceBundle, loginRoleNamesSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      loginName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, loginName, Login.class, "loginName", resourceBundle));
      fullName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, fullName, Login.class, "fullName", resourceBundle));
   }

   public Set<ConstraintViolation<Login>> validate(Login model)
   {
      Set<ConstraintViolation<Login>> violations = new HashSet<ConstraintViolation<Login>>();
      violations.addAll(textInputControlValidator.validate(loginName, Login.class, "loginName", resourceBundle));
      violations.addAll(textInputControlValidator.validate(fullName, Login.class, "fullName", resourceBundle));
      return violations;
   }

   public void bind(Login model)
   {
      loginName.textProperty().bindBidirectional(model.loginNameProperty());
      email.textProperty().bindBidirectional(model.emailProperty());
      fullName.textProperty().bindBidirectional(model.fullNameProperty());
      password.textProperty().bindBidirectional(model.passwordProperty());
      disableLogin.textProperty().bindBidirectional(model.disableLoginProperty(), new BooleanStringConverter());
      accountLocked.textProperty().bindBidirectional(model.accountLockedProperty(), new BooleanStringConverter());
      saleKey.textProperty().bindBidirectional(model.saleKeyProperty());
      credentialExpiration.calendarProperty().bindBidirectional(model.credentialExpirationProperty());
      accountExpiration.calendarProperty().bindBidirectional(model.accountExpirationProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      loginRoleNamesForm.bind(model);
      loginRoleNamesSelection.bind(model);
   }

   public TextField getLoginName()
   {
      return loginName;
   }

   public TextField getEmail()
   {
      return email;
   }

   public TextField getFullName()
   {
      return fullName;
   }

   public TextField getPassword()
   {
      return password;
   }

   public CheckBox getDisableLogin()
   {
      return disableLogin;
   }

   public CheckBox getAccountLocked()
   {
      return accountLocked;
   }

   public TextField getSaleKey()
   {
      return saleKey;
   }

   public CalendarTextField getCredentialExpiration()
   {
      return credentialExpiration;
   }

   public CalendarTextField getAccountExpiration()
   {
      return accountExpiration;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public LoginRoleNamesForm getLoginRoleNamesForm()
   {
      return loginRoleNamesForm;
   }

   public LoginRoleNamesSelection getLoginRoleNamesSelection()
   {
      return loginRoleNamesSelection;
   }
}
