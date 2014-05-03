package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookPrescriberSelection extends AbstractSelection<PrescriptionBook, Prescriber>
{

   private ComboBox<PrescriptionBookPrescriber> prescriber;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      prescriber = viewBuilder.addComboBox("PrescriptionBook_prescriber_description.title", "prescriber", resourceBundle, false);

      prescriber.setCellFactory(new Callback<ListView<PrescriptionBookPrescriber>, ListCell<PrescriptionBookPrescriber>>()
      {
         @Override
         public ListCell<PrescriptionBookPrescriber> call(ListView<PrescriptionBookPrescriber> listView)
         {
            return new PrescriptionBookPrescriberListCell();
         }
      });
      prescriber.setButtonCell(new PrescriptionBookPrescriberListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      prescriber.valueProperty().bindBidirectional(model.prescriberProperty());
   }

   public ComboBox<PrescriptionBookPrescriber> getPrescriber()
   {
      return prescriber;
   }
}
