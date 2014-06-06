package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CustomerInvoiceInvoiceItemsForm extends AbstractToManyAssociation<CustomerInvoice, CustomerInvoiceItem>
{

   private TableView<CustomerInvoiceItem> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , CustomerInvoiceItem.class
         , CustomerInvoice.class
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
      viewBuilder.addStringColumn(dataList, "internalPic", "CustomerInvoiceItem_internalPic_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "purchasedQty", "CustomerInvoiceItem_purchasedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "CustomerInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalSalesPrice", "CustomerInvoiceItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
   }

   public TableView<CustomerInvoiceItem> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
