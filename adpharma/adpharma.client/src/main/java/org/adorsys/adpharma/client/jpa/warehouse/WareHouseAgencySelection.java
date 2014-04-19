package org.adorsys.adpharma.client.jpa.warehouse;

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

public class WareHouseAgencySelection extends AbstractSelection<WareHouse, Agency>
{

   private ComboBox<WareHouseAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, WareHouse.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("WareHouse_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<WareHouseAgency>, ListCell<WareHouseAgency>>()
      {
         @Override
         public ListCell<WareHouseAgency> call(ListView<WareHouseAgency> listView)
         {
            return new WareHouseAgencyListCell();
         }
      });
      agency.setButtonCell(new WareHouseAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(WareHouse model)
   {
	   agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<WareHouseAgency> getAgency()
   {
      return agency;
   }
}
