package org.adorsys.adpharma.client.jpa.inventory;

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

public class InventoryAgencySelection extends AbstractSelection<Inventory, Agency>
{

   private ComboBox<InventoryAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Inventory_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<InventoryAgency>, ListCell<InventoryAgency>>()
      {
         @Override
         public ListCell<InventoryAgency> call(ListView<InventoryAgency> listView)
         {
            return new InventoryAgencyListCell();
         }
      });
      agency.setButtonCell(new InventoryAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<InventoryAgency> getAgency()
   {
      return agency;
   }
}
