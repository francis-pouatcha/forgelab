package org.adorsys.adpharma.client.jpa.hospital;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;

public class HospitalViewSearchFields extends AbstractForm<Hospital>
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
