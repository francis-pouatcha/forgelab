package org.adorsys.adpharma.client.jpa.prescriber;

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

public class PrescriberView extends AbstractForm<Prescriber>
{

   private TextField name;

   private TextField phone;

   private TextField street;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Prescriber_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Prescriber_phone_description.title", "phone", resourceBundle);
      street = viewBuilder.addTextField("Prescriber_street_description.title", "street", resourceBundle);
      zipCode = viewBuilder.addTextField("Prescriber_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Prescriber_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Prescriber_country_description.title", "country", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Prescriber>(textInputControlValidator, name, Prescriber.class, "name", resourceBundle));
   }

   public Set<ConstraintViolation<Prescriber>> validate(Prescriber model)
   {
      Set<ConstraintViolation<Prescriber>> violations = new HashSet<ConstraintViolation<Prescriber>>();
      violations.addAll(textInputControlValidator.validate(name, Prescriber.class, "name", resourceBundle));
      return violations;
   }

   public void bind(Prescriber model)
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
