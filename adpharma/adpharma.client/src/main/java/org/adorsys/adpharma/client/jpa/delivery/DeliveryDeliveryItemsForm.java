package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryDeliveryItemsForm extends AbstractToManyAssociation<Delivery, DeliveryItem>
{

   private TableView<DeliveryItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , DeliveryItem.class
         , Delivery.class
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
      viewBuilder.addStringColumn(dataList, "internalPic", "DeliveryItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "mainPic", "DeliveryItem_mainPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "secondaryPic", "DeliveryItem_secondaryPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "DeliveryItem_articleName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "expirationDate", "DeliveryItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
      viewBuilder.addBigDecimalColumn(dataList, "qtyOrdered", "DeliveryItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "freeQuantity", "DeliveryItem_freeQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "DeliveryItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "DeliveryItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "DeliveryItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "DeliveryItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
   }

   public TableView<DeliveryItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
