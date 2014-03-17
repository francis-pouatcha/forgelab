package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderCreatingUserForm;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderCreatingUserSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.ComboBox;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderSupplierForm;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderSupplierSelection;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderAgencyForm;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderAgencySelection;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderVatForm;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderVatSelection;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderProcurementOrderItemsForm;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderProcurementOrderItemsSelection;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderAgency;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderVat;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public class ProcurementOrderItemProcurementOrderForm extends AbstractToOneAssociation<ProcurementOrderItem, ProcurementOrder>
{

   private TextField procurementOrderNumber;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountAfterTax;

   private BigDecimalField amountDiscount;

   private BigDecimalField netAmountToPay;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      procurementOrderNumber = viewBuilder.addTextField("ProcurementOrder_procurementOrderNumber_description.title", "procurementOrderNumber", resourceBundle);
      amountBeforeTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("ProcurementOrder_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      netAmountToPay = viewBuilder.addBigDecimalField("ProcurementOrder_netAmountToPay_description.title", "netAmountToPay", resourceBundle, NumberType.CURRENCY, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
      procurementOrderNumber.textProperty().bindBidirectional(model.getProcurementOrder().procurementOrderNumberProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getProcurementOrder().amountBeforeTaxProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getProcurementOrder().amountAfterTaxProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getProcurementOrder().amountDiscountProperty());
      netAmountToPay.numberProperty().bindBidirectional(model.getProcurementOrder().netAmountToPayProperty());
   }

   public TextField getProcurementOrderNumber()
   {
      return procurementOrderNumber;
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

   public BigDecimalField getNetAmountToPay()
   {
      return netAmountToPay;
   }
}
