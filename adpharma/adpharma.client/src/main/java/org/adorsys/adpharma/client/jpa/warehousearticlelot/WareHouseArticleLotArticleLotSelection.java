package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseArticleLotArticleLotSelection extends AbstractSelection<WareHouseArticleLot, ArticleLot>
{

   private ComboBox<WareHouseArticleLotArticleLot> articleLot;

   @Inject
   @Bundle({ CrudKeys.class, ArticleLot.class, WareHouseArticleLot.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      articleLot = viewBuilder.addComboBox("WareHouseArticleLot_articleLot_description.title", "articleLot", resourceBundle, false);

      articleLot.setCellFactory(new Callback<ListView<WareHouseArticleLotArticleLot>, ListCell<WareHouseArticleLotArticleLot>>()
      {
         @Override
         public ListCell<WareHouseArticleLotArticleLot> call(ListView<WareHouseArticleLotArticleLot> listView)
         {
            return new WareHouseArticleLotArticleLotListCell();
         }
      });
      articleLot.setButtonCell(new WareHouseArticleLotArticleLotListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(WareHouseArticleLot model)
   {
	   articleLot.valueProperty().bindBidirectional(model.articleLotProperty());
   }

   public ComboBox<WareHouseArticleLotArticleLot> getArticleLot()
   {
      return articleLot;
   }
}
