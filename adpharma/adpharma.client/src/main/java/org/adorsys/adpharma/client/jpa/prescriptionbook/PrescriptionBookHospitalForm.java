package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookHospitalForm extends AbstractToOneAssociation<PrescriptionBook, Hospital>
{

   private TextField name;

   private TextField phone;

   private TextField street;

   private TextField city;

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
      city = viewBuilder.addTextField("Hospital_city_description.title", "city", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      name.textProperty().bindBidirectional(model.getHospital().nameProperty());
      phone.textProperty().bindBidirectional(model.getHospital().phoneProperty());
      street.textProperty().bindBidirectional(model.getHospital().streetProperty());
      city.textProperty().bindBidirectional(model.getHospital().cityProperty());
   }

   public void update(PrescriptionBookHospital data)
   {
      name.textProperty().set(data.nameProperty().get());
      phone.textProperty().set(data.phoneProperty().get());
      street.textProperty().set(data.streetProperty().get());
      city.textProperty().set(data.cityProperty().get());
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
