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

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookPrescriberSelection extends AbstractSelection<PrescriptionBook, Prescriber>
{

   private ComboBox<Prescriber> prescriber;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      prescriber = viewBuilder.addComboBox("PrescriptionBook_prescriber_description.title", "prescriber", resourceBundle, false);

      prescriber.setCellFactory(new Callback<ListView<Prescriber>, ListCell<Prescriber>>()
      {
         @Override
         public ListCell<Prescriber> call(ListView<Prescriber> listView)
         {
            return new PrescriptionBookPrescriberListCell();
         }
      });
      prescriber.setButtonCell(new PrescriptionBookPrescriberListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
   }

   public ComboBox<Prescriber> getPrescriber()
   {
      return prescriber;
   }
}
