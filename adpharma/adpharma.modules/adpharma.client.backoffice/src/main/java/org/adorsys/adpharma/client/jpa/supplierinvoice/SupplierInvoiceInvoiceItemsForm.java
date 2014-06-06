package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
      viewBuilder.addBigDecimalColumn(dataList, "deliveryQty", "SupplierInvoiceItem_deliveryQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "SupplierInvoiceItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "SupplierInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountReturn", "SupplierInvoiceItem_amountReturn_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "SupplierInvoiceItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
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
