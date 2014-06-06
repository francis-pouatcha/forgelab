package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseArticleLotWareHouseSelection extends AbstractSelection<WareHouseArticleLot, WareHouse>
{

   private ComboBox<WareHouseArticleLotWareHouse> wareHouse;

   @Inject
   @Bundle({ CrudKeys.class, WareHouse.class, WareHouseArticleLot.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      wareHouse = viewBuilder.addComboBox("WareHouseArticleLot_wareHouse_description.title", "wareHouse", resourceBundle, false);

      wareHouse.setCellFactory(new Callback<ListView<WareHouseArticleLotWareHouse>, ListCell<WareHouseArticleLotWareHouse>>()
      {
         @Override
         public ListCell<WareHouseArticleLotWareHouse> call(ListView<WareHouseArticleLotWareHouse> listView)
         {
            return new WareHouseArticleLotWareHouseListCell();
         }
      });
      wareHouse.setButtonCell(new WareHouseArticleLotWareHouseListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(WareHouseArticleLot model)
   {
	   wareHouse.valueProperty().bindBidirectional(model.wareHouseProperty());
   }

   public ComboBox<WareHouseArticleLotWareHouse> getWareHouse()
   {
      return wareHouse;
   }
}
