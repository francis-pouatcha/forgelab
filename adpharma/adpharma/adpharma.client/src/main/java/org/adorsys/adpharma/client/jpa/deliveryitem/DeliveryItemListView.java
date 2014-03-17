package org.adorsys.adpharma.client.jpa.deliveryitem;

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

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import de.jensd.fx.fontawesome.AwesomeIcon;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public class DeliveryItemListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<DeliveryItem> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , DeliveryItem.class
         , Article.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "internalPic", "DeliveryItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "secondaryPic", "DeliveryItem_secondaryPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "mainPic", "DeliveryItem_mainPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "DeliveryItem_articleName_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "initialQty", "DeliveryItem_initialQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "finalQty", "DeliveryItem_finalQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalPurchasingPrice", "DeliveryItem_totalPurchasingPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalDiscount", "DeliveryItem_totalDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalSalesPrice", "DeliveryItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
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

   public TableView<DeliveryItem> getDataList()
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
