package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryItemDeliveryForm extends AbstractToOneAssociation<DeliveryItem, Delivery>
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

   public void bind(DeliveryItem model)
   {
      deliveryNumber.textProperty().bindBidirectional(model.getDelivery().deliveryNumberProperty());
      deliverySlipNumber.textProperty().bindBidirectional(model.getDelivery().deliverySlipNumberProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getDelivery().amountBeforeTaxProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getDelivery().amountAfterTaxProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getDelivery().amountDiscountProperty());
      netAmountToPay.numberProperty().bindBidirectional(model.getDelivery().netAmountToPayProperty());
      dateOnDeliverySlip.calendarProperty().bindBidirectional(model.getDelivery().dateOnDeliverySlipProperty());
   }

   public void update(DeliveryItemDelivery data)
   {
      deliveryNumber.textProperty().set(data.deliveryNumberProperty().get());
      deliverySlipNumber.textProperty().set(data.deliverySlipNumberProperty().get());
      amountBeforeTax.numberProperty().set(data.amountBeforeTaxProperty().get());
      amountAfterTax.numberProperty().set(data.amountAfterTaxProperty().get());
      amountDiscount.numberProperty().set(data.amountDiscountProperty().get());
      netAmountToPay.numberProperty().set(data.netAmountToPayProperty().get());
      dateOnDeliverySlip.calendarProperty().set(data.dateOnDeliverySlipProperty().get());
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
