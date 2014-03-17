package org.adorsys.adpharma.client.jpa.customer;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.employer.EmployerCreatingUserForm;
import org.adorsys.adpharma.client.jpa.employer.EmployerCreatingUserSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.employer.Employer;

public class CustomerEmployerForm extends AbstractToOneAssociation<Customer, Employer>
{

   private TextField name;

   private TextField phone;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Employer_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Employer_phone_description.title", "phone", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Customer model)
   {
      name.textProperty().bindBidirectional(model.getEmployer().nameProperty());
      phone.textProperty().bindBidirectional(model.getEmployer().phoneProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getPhone()
   {
      return phone;
   }
}
