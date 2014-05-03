package org.adorsys.adpharma.client.jpa.supplierinvoice;

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

public class SupplierInvoiceCreatingUserForm extends AbstractToOneAssociation<SupplierInvoice, Login>
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

   public void bind(SupplierInvoice model)
   {
      loginName.textProperty().bindBidirectional(model.getCreatingUser().loginNameProperty());
      email.textProperty().bindBidirectional(model.getCreatingUser().emailProperty());
      gender.valueProperty().bindBidirectional(model.getCreatingUser().genderProperty());
   }

   public void update(SupplierInvoiceCreatingUser data)
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
