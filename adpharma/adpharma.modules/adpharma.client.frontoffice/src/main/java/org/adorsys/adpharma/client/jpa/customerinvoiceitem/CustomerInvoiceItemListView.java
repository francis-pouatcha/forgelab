package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

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

public class CustomerInvoiceItemListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<CustomerInvoiceItem> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , CustomerInvoiceItem.class
         , Article.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "internalPic", "CustomerInvoiceItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "purchasedQty", "CustomerInvoiceItem_purchasedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "CustomerInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalSalesPrice", "CustomerInvoiceItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
     
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

   public TableView<CustomerInvoiceItem> getDataList()
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
