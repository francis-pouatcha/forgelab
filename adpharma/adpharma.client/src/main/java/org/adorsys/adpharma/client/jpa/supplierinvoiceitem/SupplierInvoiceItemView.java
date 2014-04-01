package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
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
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;

public class SupplierInvoiceItemView extends AbstractForm<SupplierInvoiceItem>
{

   private TextField internalPic;

   private BigDecimalField deliveryQty;

   private BigDecimalField purchasePricePU;

   private BigDecimalField salesPricePU;

   private BigDecimalField amountReturn;

   private BigDecimalField totalPurchasePrice;

   @Inject
   private SupplierInvoiceItemInvoiceForm supplierInvoiceItemInvoiceForm;

   @Inject
   private SupplierInvoiceItemArticleForm supplierInvoiceItemArticleForm;
   @Inject
   private SupplierInvoiceItemArticleSelection supplierInvoiceItemArticleSelection;

   @Inject
   @Bundle({ CrudKeys.class, SupplierInvoiceItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("SupplierInvoiceItem_internalPic_description.title", "internalPic", resourceBundle);
      deliveryQty = viewBuilder.addBigDecimalField("SupplierInvoiceItem_deliveryQty_description.title", "deliveryQty", resourceBundle, NumberType.INTEGER, locale);
      purchasePricePU = viewBuilder.addBigDecimalField("SupplierInvoiceItem_purchasePricePU_description.title", "purchasePricePU", resourceBundle, NumberType.CURRENCY, locale);
      salesPricePU = viewBuilder.addBigDecimalField("SupplierInvoiceItem_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      amountReturn = viewBuilder.addBigDecimalField("SupplierInvoiceItem_amountReturn_description.title", "amountReturn", resourceBundle, NumberType.CURRENCY, locale);
      totalPurchasePrice = viewBuilder.addBigDecimalField("SupplierInvoiceItem_totalPurchasePrice_description.title", "totalPurchasePrice", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addTitlePane("SupplierInvoiceItem_invoice_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoiceItem_invoice_description.title", "invoice", resourceBundle, supplierInvoiceItemInvoiceForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("SupplierInvoiceItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoiceItem_article_description.title", "article", resourceBundle, supplierInvoiceItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SupplierInvoiceItem_article_description.title", "article", resourceBundle, supplierInvoiceItemArticleSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<SupplierInvoiceItem>> validate(SupplierInvoiceItem model)
   {
      Set<ConstraintViolation<SupplierInvoiceItem>> violations = new HashSet<ConstraintViolation<SupplierInvoiceItem>>();
      return violations;
   }

   public void bind(SupplierInvoiceItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      deliveryQty.numberProperty().bindBidirectional(model.deliveryQtyProperty());
      purchasePricePU.numberProperty().bindBidirectional(model.purchasePricePUProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      amountReturn.numberProperty().bindBidirectional(model.amountReturnProperty());
      totalPurchasePrice.numberProperty().bindBidirectional(model.totalPurchasePriceProperty());
      supplierInvoiceItemInvoiceForm.bind(model);
      supplierInvoiceItemArticleForm.bind(model);
      supplierInvoiceItemArticleSelection.bind(model);
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public BigDecimalField getDeliveryQty()
   {
      return deliveryQty;
   }

   public BigDecimalField getPurchasePricePU()
   {
      return purchasePricePU;
   }

   public BigDecimalField getSalesPricePU()
   {
      return salesPricePU;
   }

   public BigDecimalField getAmountReturn()
   {
      return amountReturn;
   }

   public BigDecimalField getTotalPurchasePrice()
   {
      return totalPurchasePrice;
   }

   public SupplierInvoiceItemInvoiceForm getSupplierInvoiceItemInvoiceForm()
   {
      return supplierInvoiceItemInvoiceForm;
   }

   public SupplierInvoiceItemArticleForm getSupplierInvoiceItemArticleForm()
   {
      return supplierInvoiceItemArticleForm;
   }

   public SupplierInvoiceItemArticleSelection getSupplierInvoiceItemArticleSelection()
   {
      return supplierInvoiceItemArticleSelection;
   }
}
