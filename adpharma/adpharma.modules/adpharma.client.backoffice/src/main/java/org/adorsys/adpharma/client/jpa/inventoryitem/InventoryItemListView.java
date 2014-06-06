package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class InventoryItemListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<InventoryItem> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , InventoryItem.class
         , Article.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addBigDecimalColumn(dataList, "expectedQty", "InventoryItem_expectedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "asseccedQty", "InventoryItem_asseccedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addSimpleNumberColumn(dataList, "gap", "InventoryItem_gap_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "gapSalesPricePU", "InventoryItem_gapSalesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "gapPurchasePricePU", "InventoryItem_gapPurchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "gapTotalSalePrice", "InventoryItem_gapTotalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "gapTotalPurchasePrice", "InventoryItem_gapTotalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addStringColumn(dataList, "internalPic", "InventoryItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();

      HBox buttonBar = viewBuilder.addButtonBar();
      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
      rootPane = viewBuilder.toAnchorPane();
   }

   public Button getCreateButton()
   {
      return createButton;
   }

   public Button getSearchButton()
   {
      return searchButton;
   }

   public TableView<InventoryItem> getDataList()
   {
      return dataList;
   }

   public AnchorPane getRootPane()
   {
      return rootPane;
   }

   public Pagination getPagination()
   {
      return pagination;
   }

}
