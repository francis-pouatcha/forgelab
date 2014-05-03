package org.adorsys.adpharma.client.jpa.inventory;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventoryInventoryItemsForm extends AbstractToManyAssociation<Inventory, InventoryItem>
{

   private TableView<InventoryItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , InventoryItem.class
         , Inventory.class
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

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
   }

   public TableView<InventoryItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
