package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookHospitalSelection extends AbstractSelection<PrescriptionBook, Hospital>
{

   private ComboBox<PrescriptionBookHospital> hospital;

   @Inject
   @Bundle({ CrudKeys.class, Hospital.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      hospital = viewBuilder.addComboBox("PrescriptionBook_hospital_description.title", "hospital", resourceBundle, false);

      hospital.setCellFactory(new Callback<ListView<PrescriptionBookHospital>, ListCell<PrescriptionBookHospital>>()
      {
         @Override
         public ListCell<PrescriptionBookHospital> call(ListView<PrescriptionBookHospital> listView)
         {
            return new PrescriptionBookHospitalListCell();
         }
      });
      hospital.setButtonCell(new PrescriptionBookHospitalListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      hospital.valueProperty().bindBidirectional(model.hospitalProperty());
   }

   public ComboBox<PrescriptionBookHospital> getHospital()
   {
      return hospital;
   }
}
