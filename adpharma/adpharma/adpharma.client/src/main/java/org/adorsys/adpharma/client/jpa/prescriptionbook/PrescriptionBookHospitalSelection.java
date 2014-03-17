package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookHospitalSelection extends AbstractSelection<PrescriptionBook, Hospital>
{

   private ComboBox<Hospital> hospital;

   @Inject
   @Bundle({ CrudKeys.class, Hospital.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      hospital = viewBuilder.addComboBox("PrescriptionBook_hospital_description.title", "hospital", resourceBundle, false);

      hospital.setCellFactory(new Callback<ListView<Hospital>, ListCell<Hospital>>()
      {
         @Override
         public ListCell<Hospital> call(ListView<Hospital> listView)
         {
            return new PrescriptionBookHospitalListCell();
         }
      });
      hospital.setButtonCell(new PrescriptionBookHospitalListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
   }

   public ComboBox<Hospital> getHospital()
   {
      return hospital;
   }
}
