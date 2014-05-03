package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderItemProcurementOrderForm extends AbstractToOneAssociation<ProcurementOrderItem, ProcurementOrder>
{

   private TextField procurementOrderNumber;

   private ComboBox<DocumentProcessingState> poStatus;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountAfterTax;

   private BigDecimalField amountDiscount;

   private BigDecimalField taxAmount;

   private BigDecimalField netAmountToPay;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle poStatusBundle;

   @Inject
   private DocumentProcessingStateConverter poStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory poStatusListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      procurementOrderNumber = viewBuilder.addTextField("ProcurementOrder_procurementOrderNumber_description.title", "procurementOrderNumber", resourceBundle);
      poStatus = viewBuilder.addComboBox("ProcurementOrder_poStatus_description.title", "poStatus", resourceBundle, DocumentProcessingState.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("ProcurementOrder_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      taxAmount = viewBuilder.addBigDecimalField("ProcurementOrder_taxAmount_description.title", "taxAmount", resourceBundle, NumberType.CURRENCY, locale);
      netAmountToPay = viewBuilder.addBigDecimalField("ProcurementOrder_netAmountToPay_description.title", "netAmountToPay", resourceBundle, NumberType.CURRENCY, locale);

      ComboBoxInitializer.initialize(poStatus, poStatusConverter, poStatusListCellFatory, poStatusBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
      procurementOrderNumber.textProperty().bindBidirectional(model.getProcurementOrder().procurementOrderNumberProperty());
      poStatus.valueProperty().bindBidirectional(model.getProcurementOrder().poStatusProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getProcurementOrder().amountBeforeTaxProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getProcurementOrder().amountAfterTaxProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getProcurementOrder().amountDiscountProperty());
      taxAmount.numberProperty().bindBidirectional(model.getProcurementOrder().taxAmountProperty());
      netAmountToPay.numberProperty().bindBidirectional(model.getProcurementOrder().netAmountToPayProperty());
   }

   public void update(ProcurementOrderItemProcurementOrder data)
   {
      procurementOrderNumber.textProperty().set(data.procurementOrderNumberProperty().get());
      poStatus.valueProperty().set(data.poStatusProperty().get());
      amountBeforeTax.numberProperty().set(data.amountBeforeTaxProperty().get());
      amountAfterTax.numberProperty().set(data.amountAfterTaxProperty().get());
      amountDiscount.numberProperty().set(data.amountDiscountProperty().get());
      taxAmount.numberProperty().set(data.taxAmountProperty().get());
      netAmountToPay.numberProperty().set(data.netAmountToPayProperty().get());
   }

   public TextField getProcurementOrderNumber()
   {
      return procurementOrderNumber;
   }

   public ComboBox<DocumentProcessingState> getPoStatus()
   {
      return poStatus;
   }

   public BigDecimalField getAmountBeforeTax()
   {
      return amountBeforeTax;
   }

   public BigDecimalField getAmountAfterTax()
   {
      return amountAfterTax;
   }

   public BigDecimalField getAmountDiscount()
   {
      return amountDiscount;
   }

   public BigDecimalField getTaxAmount()
   {
      return taxAmount;
   }

   public BigDecimalField getNetAmountToPay()
   {
      return netAmountToPay;
   }
}
