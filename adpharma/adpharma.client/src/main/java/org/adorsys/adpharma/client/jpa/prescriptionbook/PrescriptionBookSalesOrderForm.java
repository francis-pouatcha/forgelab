package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCashDrawerForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCashDrawerSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomerForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomerSelection;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderInsuranceForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderInsuranceSelection;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderVatForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderVatSelection;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesAgentForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesAgentSelection;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderAgencyForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderAgencySelection;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesOrderItemsForm;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesOrderItemsSelection;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCashDrawer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderInsurance;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderVat;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesAgent;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderAgency;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeListCellFatory;

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
