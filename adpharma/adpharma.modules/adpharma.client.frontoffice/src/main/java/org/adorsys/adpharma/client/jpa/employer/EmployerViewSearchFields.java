package org.adorsys.adpharma.client.jpa.employer;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class EmployerViewSearchFields extends AbstractForm<Employer>
{

   private TextField name;

   private TextField phone;

   private TextField zipCode;

   private TextField city;

   private TextField country;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Employer_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Employer_phone_description.title", "phone", resourceBundle);
      zipCode = viewBuilder.addTextField("Employer_zipCode_description.title", "zipCode", resourceBundle);
      city = viewBuilder.addTextField("Employer_city_description.title", "city", resourceBundle);
      country = viewBuilder.addTextField("Employer_country_description.title", "country", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Employer model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      phone.textProperty().bindBidirectional(model.phoneProperty());
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
