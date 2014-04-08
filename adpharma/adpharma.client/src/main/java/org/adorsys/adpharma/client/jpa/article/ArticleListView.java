package org.adorsys.adpharma.client.jpa.article;

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
import org.adorsys.adpharma.client.jpa.section.Section;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import java.math.BigDecimal;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import javafx.beans.property.SimpleLongProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<Article> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , Article.class
         , Agency.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "pic", "Article_pic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "manufacturer", "Article_manufacturer_description.title", resourceBundle);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(dataList, "qtyInStock", "Article_qtyInStock_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "sppu", "Article_sppu_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "pppu", "Article_pppu_description.title", resourceBundle, NumberType.CURRENCY, locale);
     
      viewBuilder.addDateColumn(dataList, "lastStockEntry", "Article_lastStockEntry_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addDateColumn(dataList, "lastOutOfStock", "Article_lastOutOfStock_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      // Field not displayed in table
      viewBuilder.addStringColumn(dataList, "agency", "Article_agency_description.title", resourceBundle);
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

   public TableView<Article> getDataList()
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
