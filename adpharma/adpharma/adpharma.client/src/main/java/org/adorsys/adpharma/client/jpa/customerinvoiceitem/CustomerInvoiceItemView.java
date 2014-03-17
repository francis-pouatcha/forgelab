package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import java.text.NumberFormat;
import java.util.Locale;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;

public class CustomerInvoiceItemView extends AbstractForm<CustomerInvoiceItem>
{

   private TextField internalPic;

   private TextField indexLine;

   private BigDecimalField purchasedQty;

   private BigDecimalField salesPricePU;

   private BigDecimalField totalSalesPrice;

   @Inject
   private CustomerInvoiceItemInvoiceForm customerInvoiceItemInvoiceForm;

   @Inject
   private CustomerInvoiceItemArticleForm customerInvoiceItemArticleForm;
   @Inject
   private CustomerInvoiceItemArticleSelection customerInvoiceItemArticleSelection;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoiceItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("CustomerInvoiceItem_internalPic_description.title", "internalPic", resourceBundle);
      indexLine = viewBuilder.addTextField("CustomerInvoiceItem_indexLine_description.title", "indexLine", resourceBundle);
      purchasedQty = viewBuilder.addBigDecimalField("CustomerInvoiceItem_purchasedQty_description.title", "purchasedQty", resourceBundle, NumberType.INTEGER, locale);
      salesPricePU = viewBuilder.addBigDecimalField("CustomerInvoiceItem_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      totalSalesPrice = viewBuilder.addBigDecimalField("CustomerInvoiceItem_totalSalesPrice_description.title", "totalSalesPrice", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addTitlePane("CustomerInvoiceItem_invoice_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoiceItem_invoice_description.title", "invoice", resourceBundle, customerInvoiceItemInvoiceForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("CustomerInvoiceItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoiceItem_article_description.title", "article", resourceBundle, customerInvoiceItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoiceItem_article_description.title", "article", resourceBundle, customerInvoiceItemArticleSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<CustomerInvoiceItem>> validate(CustomerInvoiceItem model)
   {
      Set<ConstraintViolation<CustomerInvoiceItem>> violations = new HashSet<ConstraintViolation<CustomerInvoiceItem>>();
      return violations;
   }

   public void bind(CustomerInvoiceItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      indexLine.textProperty().bindBidirectional(model.indexLineProperty(), NumberFormat.getInstance(locale));
      purchasedQty.numberProperty().bindBidirectional(model.purchasedQtyProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      totalSalesPrice.numberProperty().bindBidirectional(model.totalSalesPriceProperty());
      customerInvoiceItemInvoiceForm.bind(model);
      customerInvoiceItemArticleForm.bind(model);
      customerInvoiceItemArticleSelection.bind(model);
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getIndexLine()
   {
      return indexLine;
   }

   public BigDecimalField getPurchasedQty()
   {
      return purchasedQty;
   }

   public BigDecimalField getSalesPricePU()
   {
      return salesPricePU;
   }

   public BigDecimalField getTotalSalesPrice()
   {
      return totalSalesPrice;
   }

   public CustomerInvoiceItemInvoiceForm getCustomerInvoiceItemInvoiceForm()
   {
      return customerInvoiceItemInvoiceForm;
   }

   public CustomerInvoiceItemArticleForm getCustomerInvoiceItemArticleForm()
   {
      return customerInvoiceItemArticleForm;
   }

   public CustomerInvoiceItemArticleSelection getCustomerInvoiceItemArticleSelection()
   {
      return customerInvoiceItemArticleSelection;
   }
}
