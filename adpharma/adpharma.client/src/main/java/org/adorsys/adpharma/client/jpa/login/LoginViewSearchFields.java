package org.adorsys.adpharma.client.jpa.login;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class LoginViewSearchFields extends AbstractForm<Login>
{

   private TextField loginName;

   private TextField email;

   private TextField fullName;

   private TextField password;

   private CheckBox disableLogin;

   private CheckBox accountLocked;

   private TextField saleKey;

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

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      loginName = viewBuilder.addTextField("Login_loginName_description.title", "loginName", resourceBundle);
//      email = viewBuilder.addTextField("Login_email_description.title", "email", resourceBundle);
      fullName = viewBuilder.addTextField("Login_fullName_description.title", "fullName", resourceBundle);
//      password = viewBuilder.addTextField("Login_password_description.title", "password", resourceBundle);
      disableLogin = viewBuilder.addCheckBox("Login_disableLogin_description.title", "disableLogin", resourceBundle);
      accountLocked = viewBuilder.addCheckBox("Login_accountLocked_description.title", "accountLocked", resourceBundle);
      saleKey = viewBuilder.addTextField("Login_saleKey_description.title", "saleKey", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Login model)
   {
      loginName.textProperty().bindBidirectional(model.loginNameProperty());
//      email.textProperty().bindBidirectional(model.emailProperty());
      fullName.textProperty().bindBidirectional(model.fullNameProperty());
//      password.textProperty().bindBidirectional(model.passwordProperty());
      disableLogin.textProperty().bindBidirectional(model.disableLoginProperty(), new BooleanStringConverter());
      accountLocked.textProperty().bindBidirectional(model.accountLockedProperty(), new BooleanStringConverter());
      saleKey.textProperty().bindBidirectional(model.saleKeyProperty());

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
}
