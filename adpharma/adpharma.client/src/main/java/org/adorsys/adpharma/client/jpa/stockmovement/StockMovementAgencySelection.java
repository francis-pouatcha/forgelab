package org.adorsys.adpharma.client.jpa.stockmovement;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.stockmovement.StockMovement;

public class StockMovementAgencySelection extends AbstractSelection<StockMovement, Agency>
{

   private ComboBox<StockMovementAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, StockMovement.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("StockMovement_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<StockMovementAgency>, ListCell<StockMovementAgency>>()
      {
         @Override
         public ListCell<StockMovementAgency> call(ListView<StockMovementAgency> listView)
         {
            return new StockMovementAgencyListCell();
         }
      });
      agency.setButtonCell(new StockMovementAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(StockMovement model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<StockMovementAgency> getAgency()
   {
      return agency;
   }
}
