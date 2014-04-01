package org.adorsys.adpharma.client.jpa.employer;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.employer.Employer;

public class EmployerView extends AbstractForm<Employer>
{

   private TextField name;

   private TextField phone;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   @Inject
   private EmployerCreatingUserForm employerCreatingUserForm;
   @Inject
   private EmployerCreatingUserSelection employerCreatingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Employer_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Employer_phone_description.title", "phone", resourceBundle);
      zipCode = viewBuilder.addTextField("Employer_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Employer_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Employer_country_description.title", "country", resourceBundle);
      viewBuilder.addTitlePane("Employer_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("Employer_creatingUser_description.title", "creatingUser", resourceBundle, employerCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Employer_creatingUser_description.title", "creatingUser", resourceBundle, employerCreatingUserSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Employer>(textInputControlValidator, name, Employer.class, "name", resourceBundle));
      // no active validator
   }

   public Set<ConstraintViolation<Employer>> validate(Employer model)
   {
      Set<ConstraintViolation<Employer>> violations = new HashSet<ConstraintViolation<Employer>>();
      violations.addAll(textInputControlValidator.validate(name, Employer.class, "name", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(employerCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), Employer.class, "creatingUser", resourceBundle));
      return violations;
   }

   public void bind(Employer model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      phone.textProperty().bindBidirectional(model.phoneProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      city.textProperty().bindBidirectional(model.cityProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
      employerCreatingUserForm.bind(model);
      employerCreatingUserSelection.bind(model);
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getZipCode()
   {
      return zipCode;
   }

   public TextField getCity()
   {
      return city;
   }

   public TextField getCountry()
   {
      return country;
   }

   public EmployerCreatingUserForm getEmployerCreatingUserForm()
   {
      return employerCreatingUserForm;
   }

   public EmployerCreatingUserSelection getEmployerCreatingUserSelection()
   {
      return employerCreatingUserSelection;
   }
}
