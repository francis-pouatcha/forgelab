package org.adorsys.adpharma.client.jpa.login;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class UserInfoView extends AbstractForm<Login>
{

	   private TextField loginName;

	   private TextField email;

	   private TextField fullName;

	   private TextField password;

	   private TextField saleKey;

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

	   @Inject
	   private Locale locale;

	   @Inject
	   private TextInputControlValidator textInputControlValidator;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();
	      loginName = viewBuilder.addTextField("Login_loginName_description.title", "loginName", resourceBundle,ViewModel.READ_ONLY);
	      email = viewBuilder.addTextField("Login_email_description.title", "email", resourceBundle);
	      fullName = viewBuilder.addTextField("Login_fullName_description.title", "fullName", resourceBundle);
	      password = viewBuilder.addTextField("Login_password_description.title", "password", resourceBundle);
	      saleKey = viewBuilder.addTextField("Login_saleKey_description.title", "saleKey", resourceBundle,ViewModel.READ_ONLY);
	      gender = viewBuilder.addComboBox("Login_gender_description.title", "gender", resourceBundle, Gender.values());
	      ComboBoxInitializer.initialize(gender, genderConverter, genderListCellFatory, genderBundle);

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
	      saleKey.textProperty().bindBidirectional(model.saleKeyProperty());
	      gender.valueProperty().bindBidirectional(model.genderProperty());
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


	   public TextField getSaleKey()
	   {
	      return saleKey;
	   }

	   public ComboBox<Gender> getGender()
	   {
	      return gender;
	   }



}
