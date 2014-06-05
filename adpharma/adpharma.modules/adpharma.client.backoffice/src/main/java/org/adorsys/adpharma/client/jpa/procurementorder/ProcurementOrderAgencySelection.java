package org.adorsys.adpharma.client.jpa.procurementorder;

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

public class ProcurementOrderAgencySelection extends AbstractSelection<ProcurementOrder, Agency>
{

   private ComboBox<ProcurementOrderAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("ProcurementOrder_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<ProcurementOrderAgency>, ListCell<ProcurementOrderAgency>>()
      {
         @Override
         public ListCell<ProcurementOrderAgency> call(ListView<ProcurementOrderAgency> listView)
         {
            return new ProcurementOrderAgencyListCell();
         }
      });
      agency.setButtonCell(new ProcurementOrderAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<ProcurementOrderAgency> getAgency()
   {
      return agency;
   }
}
