package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;

public class PrescriptionBookPrescriberForm extends AbstractToOneAssociation<PrescriptionBook, Prescriber>
{

   private TextField name;

   private TextField phone;

   private TextField street;

   private TextField city;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Prescriber_name_description.title", "name", resourceBundle);
      phone = viewBuilder.addTextField("Prescriber_phone_description.title", "phone", resourceBundle);
      street = viewBuilder.addTextField("Prescriber_street_description.title", "street", resourceBundle);
      city = viewBuilder.addTextField("Prescriber_city_description.title", "city", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      name.textProperty().bindBidirectional(model.getPrescriber().nameProperty());
      phone.textProperty().bindBidirectional(model.getPrescriber().phoneProperty());
      street.textProperty().bindBidirectional(model.getPrescriber().streetProperty());
      city.textProperty().bindBidirectional(model.getPrescriber().cityProperty());
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

   public TextField getCity()
   {
      return city;
   }
}
