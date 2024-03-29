package org.adorsys.adpharma.client.jpa.hospital;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class HospitalView extends AbstractForm<Hospital>
{

   private TextField name;

   private TextField phone;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   @Inject
   @Bundle({ CrudKeys.class, Hospital.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Hospital_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Hospital_phone_description.title", "phone", resourceBundle);
      street = viewBuilder.addTextField("Hospital_street_description.title", "street", resourceBundle);
      zipCode = viewBuilder.addTextField("Hospital_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Hospital_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Hospital_country_description.title", "country", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Hospital>(textInputControlValidator, name, Hospital.class, "name", resourceBundle));
   }

   public Set<ConstraintViolation<Hospital>> validate(Hospital model)
   {
      Set<ConstraintViolation<Hospital>> violations = new HashSet<ConstraintViolation<Hospital>>();
      violations.addAll(textInputControlValidator.validate(name, Hospital.class, "name", resourceBundle));
      return violations;
   }

   public void bind(Hospital model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      phone.textProperty().bindBidirectional(model.phoneProperty());
      street.textProperty().bindBidirectional(model.streetProperty());
      zipCode.textProperty().bindBidirectional(model.zipCodeProperty());
      city.textProperty().bindBidirectional(model.cityProperty());
      country.textProperty().bindBidirectional(model.countryProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public TextField getPhone()
   {
      return phone;
   }

   public TextField getStreet()
   {
      return street;
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
}
