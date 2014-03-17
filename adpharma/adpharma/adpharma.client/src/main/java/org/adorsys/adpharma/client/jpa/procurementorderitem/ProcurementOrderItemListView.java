package org.adorsys.adpharma.client.jpa.procurementorderitem;

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

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;

public class ProcurementOrderItemListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<ProcurementOrderItem> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , ProcurementOrderItem.class
         , Article.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "lineIndex", "ProcurementOrderItem_lineIndex_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "internalPic", "ProcurementOrderItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "mainPic", "ProcurementOrderItem_mainPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "secondaryPic", "ProcurementOrderItem_secondaryPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "ProcurementOrderItem_articleName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "expirationDate", "ProcurementOrderItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
      viewBuilder.addBigDecimalColumn(dataList, "qtyOrdered", "ProcurementOrderItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "freeQuantity", "ProcurementOrderItem_freeQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "ProcurementOrderItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "ProcurementOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "ProcurementOrderItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "ProcurementOrderItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
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

   public TableView<ProcurementOrderItem> getDataList()
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
