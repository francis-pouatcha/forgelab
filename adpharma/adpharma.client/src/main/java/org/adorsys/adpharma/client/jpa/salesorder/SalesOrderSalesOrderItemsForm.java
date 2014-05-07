package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderSalesOrderItemsForm extends AbstractToManyAssociation<SalesOrder, SalesOrderItem>
{

   private TableView<SalesOrderItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , SalesOrderItem.class
         , SalesOrder.class
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
      viewBuilder.addBigDecimalColumn(dataList, "orderedQty", "SalesOrderItem_orderedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "returnedQty", "SalesOrderItem_returnedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "deliveredQty", "SalesOrderItem_deliveredQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "SalesOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalSalePrice", "SalesOrderItem_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addStringColumn(dataList, "internalPic", "SalesOrderItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
   }

   public TableView<SalesOrderItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
