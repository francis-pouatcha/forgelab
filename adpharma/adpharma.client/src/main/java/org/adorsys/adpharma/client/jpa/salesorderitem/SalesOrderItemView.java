package org.adorsys.adpharma.client.jpa.salesorderitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

public class SalesOrderItemView extends AbstractForm<SalesOrderItem>
{

   private TextField internalPic;

   private BigDecimalField orderedQty;

   private BigDecimalField returnedQty;

   private BigDecimalField deliveredQty;

   private BigDecimalField salesPricePU;

   private BigDecimalField totalSalePrice;

   private CalendarTextField recordDate;

   @Inject
   private SalesOrderItemSalesOrderForm salesOrderItemSalesOrderForm;

   @Inject
   private SalesOrderItemArticleForm salesOrderItemArticleForm;
   @Inject
   private SalesOrderItemArticleSelection salesOrderItemArticleSelection;

   @Inject
   private SalesOrderItemVatForm salesOrderItemVatForm;
   @Inject
   private SalesOrderItemVatSelection salesOrderItemVatSelection;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrderItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("SalesOrderItem_internalPic_description.title", "internalPic", resourceBundle);
      orderedQty = viewBuilder.addBigDecimalField("SalesOrderItem_orderedQty_description.title", "orderedQty", resourceBundle, NumberType.INTEGER, locale);
      returnedQty = viewBuilder.addBigDecimalField("SalesOrderItem_returnedQty_description.title", "returnedQty", resourceBundle, NumberType.INTEGER, locale);
      deliveredQty = viewBuilder.addBigDecimalField("SalesOrderItem_deliveredQty_description.title", "deliveredQty", resourceBundle, NumberType.INTEGER, locale);
      salesPricePU = viewBuilder.addBigDecimalField("SalesOrderItem_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      totalSalePrice = viewBuilder.addBigDecimalField("SalesOrderItem_totalSalePrice_description.title", "totalSalePrice", resourceBundle, NumberType.CURRENCY, locale);
      recordDate = viewBuilder.addCalendarTextField("SalesOrderItem_recordDate_description.title", "recordDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("SalesOrderItem_salesOrder_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrderItem_salesOrder_description.title", "salesOrder", resourceBundle, salesOrderItemSalesOrderForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("SalesOrderItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrderItem_article_description.title", "article", resourceBundle, salesOrderItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrderItem_article_description.title", "article", resourceBundle, salesOrderItemArticleSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrderItem_vat_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrderItem_vat_description.title", "vat", resourceBundle, salesOrderItemVatForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrderItem_vat_description.title", "vat", resourceBundle, salesOrderItemVatSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      internalPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<SalesOrderItem>(textInputControlValidator, internalPic, SalesOrderItem.class, "internalPic", resourceBundle));
      // no active validator
   }

   public Set<ConstraintViolation<SalesOrderItem>> validate(SalesOrderItem model)
   {
      Set<ConstraintViolation<SalesOrderItem>> violations = new HashSet<ConstraintViolation<SalesOrderItem>>();
      violations.addAll(textInputControlValidator.validate(internalPic, SalesOrderItem.class, "internalPic", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(salesOrderItemArticleSelection.getArticle(), model.getArticle(), SalesOrderItem.class, "article", resourceBundle));
      return violations;
   }

   public void bind(SalesOrderItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      orderedQty.numberProperty().bindBidirectional(model.orderedQtyProperty());
      returnedQty.numberProperty().bindBidirectional(model.returnedQtyProperty());
      deliveredQty.numberProperty().bindBidirectional(model.deliveredQtyProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      totalSalePrice.numberProperty().bindBidirectional(model.totalSalePriceProperty());
      recordDate.calendarProperty().bindBidirectional(model.recordDateProperty());
      salesOrderItemSalesOrderForm.bind(model);
      salesOrderItemArticleForm.bind(model);
      salesOrderItemArticleSelection.bind(model);
      salesOrderItemVatForm.bind(model);
      salesOrderItemVatSelection.bind(model);
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public BigDecimalField getOrderedQty()
   {
      return orderedQty;
   }

   public BigDecimalField getReturnedQty()
   {
      return returnedQty;
   }

   public BigDecimalField getDeliveredQty()
   {
      return deliveredQty;
   }

   public BigDecimalField getSalesPricePU()
   {
      return salesPricePU;
   }

   public BigDecimalField getTotalSalePrice()
   {
      return totalSalePrice;
   }

   public CalendarTextField getRecordDate()
   {
      return recordDate;
   }

   public SalesOrderItemSalesOrderForm getSalesOrderItemSalesOrderForm()
   {
      return salesOrderItemSalesOrderForm;
   }

   public SalesOrderItemArticleForm getSalesOrderItemArticleForm()
   {
      return salesOrderItemArticleForm;
   }

   public SalesOrderItemArticleSelection getSalesOrderItemArticleSelection()
   {
      return salesOrderItemArticleSelection;
   }

   public SalesOrderItemVatForm getSalesOrderItemVatForm()
   {
      return salesOrderItemVatForm;
   }

   public SalesOrderItemVatSelection getSalesOrderItemVatSelection()
   {
      return salesOrderItemVatSelection;
   }
}
