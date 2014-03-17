package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
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

public class ProcurementOrderCreatingUserForm extends AbstractToOneAssociation<ProcurementOrder, Login>
{

   private TextField loginName;

   private TextField email;

   @Inject
   @Bundle({ CrudKeys.class, Login.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      loginName = viewBuilder.addTextField("Login_loginName_description.title", "loginName", resourceBundle);
      email = viewBuilder.addTextField("Login_email_description.title", "email", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      loginName.textProperty().bindBidirectional(model.getCreatingUser().loginNameProperty());
      email.textProperty().bindBidirectional(model.getCreatingUser().emailProperty());
   }

   public TextField getLoginName()
   {
      return loginName;
   }

   public TextField getEmail()
   {
      return email;
   }
}
