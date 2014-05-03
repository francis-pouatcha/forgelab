package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderProcurementOrderItemsForm extends AbstractToManyAssociation<ProcurementOrder, ProcurementOrderItem>
{

   private TableView<ProcurementOrderItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , ProcurementOrderItem.class
         , ProcurementOrder.class
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

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
   }

   public TableView<ProcurementOrderItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
