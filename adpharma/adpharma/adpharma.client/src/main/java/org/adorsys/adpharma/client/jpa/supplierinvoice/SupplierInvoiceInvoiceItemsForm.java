package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItemArticleForm;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItemArticleSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import java.text.NumberFormat;
import java.util.Locale;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItemInvoiceForm;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItemArticle;

import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import org.adorsys.adpharma.client.jpa.article.Article;

public class SupplierInvoiceInvoiceItemsForm extends AbstractToManyAssociation<SupplierInvoice, SupplierInvoiceItem>
{

   private TableView<SupplierInvoiceItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , SupplierInvoiceItem.class
         , SupplierInvoice.class
         , Article.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "internalPic", "SupplierInvoiceItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addSimpleNumberColumn(dataList, "indexLine", "SupplierInvoiceItem_indexLine_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "deliveryQty", "SupplierInvoiceItem_deliveryQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "SupplierInvoiceItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "SupplierInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountReturn", "SupplierInvoiceItem_amountReturn_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalSalesPrice", "SupplierInvoiceItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
   }

   public TableView<SupplierInvoiceItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
