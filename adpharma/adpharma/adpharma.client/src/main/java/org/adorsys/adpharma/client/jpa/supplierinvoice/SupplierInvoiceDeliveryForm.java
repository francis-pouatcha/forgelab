package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCreatingUserForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCreatingUserSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySupplierForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySupplierSelection;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryAgencyForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryAgencySelection;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryVatForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryVatSelection;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCurrencyForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCurrencySelection;
import javafx.scene.control.ComboBox;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReceivingAgencyForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReceivingAgencySelection;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryDeliveryItemsForm;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryDeliveryItemsSelection;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryAgency;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryVat;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class SupplierInvoiceDeliveryForm extends AbstractToOneAssociation<SupplierInvoice, Delivery>
{

   private TextField deliveryNumber;

   private TextField deliverySlipNumber;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountAfterTax;

   private BigDecimalField amountDiscount;

   private BigDecimalField netAmountToPay;

   private CalendarTextField dateOnDeliverySlip;

   @Inject
   @Bundle({ CrudKeys.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      deliveryNumber = viewBuilder.addTextField("Delivery_deliveryNumber_description.title", "deliveryNumber", resourceBundle);
      deliverySlipNumber = viewBuilder.addTextField("Delivery_deliverySlipNumber_description.title", "deliverySlipNumber", resourceBundle);
      amountBeforeTax = viewBuilder.addBigDecimalField("Delivery_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("Delivery_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("Delivery_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      netAmountToPay = viewBuilder.addBigDecimalField("Delivery_netAmountToPay_description.title", "netAmountToPay", resourceBundle, NumberType.CURRENCY, locale);
      dateOnDeliverySlip = viewBuilder.addCalendarTextField("Delivery_dateOnDeliverySlip_description.title", "dateOnDeliverySlip", resourceBundle, "dd-MM-yyyy", locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
      deliveryNumber.textProperty().bindBidirectional(model.getDelivery().deliveryNumberProperty());
      deliverySlipNumber.textProperty().bindBidirectional(model.getDelivery().deliverySlipNumberProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getDelivery().amountBeforeTaxProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getDelivery().amountAfterTaxProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getDelivery().amountDiscountProperty());
      netAmountToPay.numberProperty().bindBidirectional(model.getDelivery().netAmountToPayProperty());
      dateOnDeliverySlip.calendarProperty().bindBidirectional(model.getDelivery().dateOnDeliverySlipProperty());
   }

   public TextField getDeliveryNumber()
   {
      return deliveryNumber;
   }

   public TextField getDeliverySlipNumber()
   {
      return deliverySlipNumber;
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

   public CalendarTextField getDateOnDeliverySlip()
   {
      return dateOnDeliverySlip;
   }
}
