package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookSalesOrderForm extends AbstractToOneAssociation<PrescriptionBook, SalesOrder>
{

   private TextField soNumber;

   private CheckBox cashed;

   private ComboBox<DocumentProcessingState> salesOrderStatus;

   private ComboBox<SalesOrderType> salesOrderType;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountVAT;

   private BigDecimalField amountDiscount;

   private BigDecimalField totalReturnAmount;

   private BigDecimalField amountAfterTax;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle salesOrderStatusBundle;

   @Inject
   private DocumentProcessingStateConverter salesOrderStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory salesOrderStatusListCellFatory;
   @Inject
   @Bundle(SalesOrderType.class)
   private ResourceBundle salesOrderTypeBundle;

   @Inject
   private SalesOrderTypeConverter salesOrderTypeConverter;

   @Inject
   private SalesOrderTypeListCellFatory salesOrderTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      soNumber = viewBuilder.addTextField("SalesOrder_soNumber_description.title", "soNumber", resourceBundle);
      cashed = viewBuilder.addCheckBox("SalesOrder_cashed_description.title", "cashed", resourceBundle);
      salesOrderStatus = viewBuilder.addComboBox("SalesOrder_salesOrderStatus_description.title", "salesOrderStatus", resourceBundle, DocumentProcessingState.values());
      salesOrderType = viewBuilder.addComboBox("SalesOrder_salesOrderType_description.title", "salesOrderType", resourceBundle, SalesOrderType.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("SalesOrder_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.CURRENCY, locale);
      amountVAT = viewBuilder.addBigDecimalField("SalesOrder_amountVAT_description.title", "amountVAT", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("SalesOrder_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      totalReturnAmount = viewBuilder.addBigDecimalField("SalesOrder_totalReturnAmount_description.title", "totalReturnAmount", resourceBundle, NumberType.CURRENCY, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("SalesOrder_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);

      ComboBoxInitializer.initialize(salesOrderStatus, salesOrderStatusConverter, salesOrderStatusListCellFatory, salesOrderStatusBundle);
      ComboBoxInitializer.initialize(salesOrderType, salesOrderTypeConverter, salesOrderTypeListCellFatory, salesOrderTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      soNumber.textProperty().bindBidirectional(model.getSalesOrder().soNumberProperty());
      cashed.textProperty().bindBidirectional(model.getSalesOrder().cashedProperty(), new BooleanStringConverter());
      salesOrderStatus.valueProperty().bindBidirectional(model.getSalesOrder().salesOrderStatusProperty());
      salesOrderType.valueProperty().bindBidirectional(model.getSalesOrder().salesOrderTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getSalesOrder().amountBeforeTaxProperty());
      amountVAT.numberProperty().bindBidirectional(model.getSalesOrder().amountVATProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getSalesOrder().amountDiscountProperty());
      totalReturnAmount.numberProperty().bindBidirectional(model.getSalesOrder().totalReturnAmountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getSalesOrder().amountAfterTaxProperty());
   }

   public void update(PrescriptionBookSalesOrder data)
   {
      soNumber.textProperty().set(data.soNumberProperty().get());
      cashed.textProperty().set(new BooleanStringConverter().toString(data.cashedProperty().get()));
      salesOrderStatus.valueProperty().set(data.salesOrderStatusProperty().get());
      salesOrderType.valueProperty().set(data.salesOrderTypeProperty().get());
      amountBeforeTax.numberProperty().set(data.amountBeforeTaxProperty().get());
      amountVAT.numberProperty().set(data.amountVATProperty().get());
      amountDiscount.numberProperty().set(data.amountDiscountProperty().get());
      totalReturnAmount.numberProperty().set(data.totalReturnAmountProperty().get());
      amountAfterTax.numberProperty().set(data.amountAfterTaxProperty().get());
   }

   public TextField getSoNumber()
   {
      return soNumber;
   }

   public CheckBox getCashed()
   {
      return cashed;
   }

   public ComboBox<DocumentProcessingState> getSalesOrderStatus()
   {
      return salesOrderStatus;
   }

   public ComboBox<SalesOrderType> getSalesOrderType()
   {
      return salesOrderType;
   }

   public BigDecimalField getAmountBeforeTax()
   {
      return amountBeforeTax;
   }

   public BigDecimalField getAmountVAT()
   {
      return amountVAT;
   }

   public BigDecimalField getAmountDiscount()
   {
      return amountDiscount;
   }

   public BigDecimalField getTotalReturnAmount()
   {
      return totalReturnAmount;
   }

   public BigDecimalField getAmountAfterTax()
   {
      return amountAfterTax;
   }
}
