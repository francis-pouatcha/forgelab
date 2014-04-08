package org.adorsys.adpharma.client.jpa.cashout;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.gender.GenderConverter;
import org.adorsys.adpharma.client.jpa.gender.GenderListCellFatory;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CashOutCashierForm extends AbstractToOneAssociation<CashOut, Login>
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

	   public void bind(CashOut model)
	   {
	      loginName.textProperty().bindBidirectional(model.getCashier().loginNameProperty());
	      email.textProperty().bindBidirectional(model.getCashier().emailProperty());
	      gender.valueProperty().bindBidirectional(model.getCashier().genderProperty());
	   }

	   public void update(CashOutCashier data)
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