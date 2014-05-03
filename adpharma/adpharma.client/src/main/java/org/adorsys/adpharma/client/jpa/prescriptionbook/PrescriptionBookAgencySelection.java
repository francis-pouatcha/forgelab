package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookAgencySelection extends AbstractSelection<PrescriptionBook, Agency>
{

   private ComboBox<PrescriptionBookAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("PrescriptionBook_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<PrescriptionBookAgency>, ListCell<PrescriptionBookAgency>>()
      {
         @Override
         public ListCell<PrescriptionBookAgency> call(ListView<PrescriptionBookAgency> listView)
         {
            return new PrescriptionBookAgencyListCell();
         }
      });
      agency.setButtonCell(new PrescriptionBookAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<PrescriptionBookAgency> getAgency()
   {
      return agency;
   }
}
