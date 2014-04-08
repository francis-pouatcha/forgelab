package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgencyForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgencySelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashierForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashierSelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawerForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawerSelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentInvoicesForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentInvoicesSelection;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidByForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBySelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaymentItemsForm;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaymentItemsSelection;
import org.adorsys.adpharma.client.jpa.payment.PaymentAgency;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashier;
import org.adorsys.adpharma.client.jpa.payment.PaymentCashDrawer;
import org.adorsys.adpharma.client.jpa.payment.PaymentPaidBy;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;

public class PaymentItemPaymentForm extends AbstractToOneAssociation<PaymentItem, Payment>
{

   private TextField paymentNumber;

   private CheckBox paymentReceiptPrinted;

   private ComboBox<PaymentMode> paymentMode;

   private BigDecimalField amount;

   private BigDecimalField receivedAmount;

   private BigDecimalField difference;

   private CalendarTextField paymentDate;

   private CalendarTextField recordDate;

   @Inject
   @Bundle({ CrudKeys.class, Payment.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(PaymentMode.class)
   private ResourceBundle paymentModeBundle;

   @Inject
   private PaymentModeConverter paymentModeConverter;

   @Inject
   private PaymentModeListCellFatory paymentModeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      paymentNumber = viewBuilder.addTextField("Payment_paymentNumber_description.title", "paymentNumber", resourceBundle);
      paymentReceiptPrinted = viewBuilder.addCheckBox("Payment_paymentReceiptPrinted_description.title", "paymentReceiptPrinted", resourceBundle);
      paymentMode = viewBuilder.addComboBox("Payment_paymentMode_description.title", "paymentMode", resourceBundle, PaymentMode.values());
      amount = viewBuilder.addBigDecimalField("Payment_amount_description.title", "amount", resourceBundle, NumberType.CURRENCY, locale);
      receivedAmount = viewBuilder.addBigDecimalField("Payment_receivedAmount_description.title", "receivedAmount", resourceBundle, NumberType.CURRENCY, locale);
      difference = viewBuilder.addBigDecimalField("Payment_difference_description.title", "difference", resourceBundle, NumberType.CURRENCY, locale);
      paymentDate = viewBuilder.addCalendarTextField("Payment_paymentDate_description.title", "paymentDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      recordDate = viewBuilder.addCalendarTextField("Payment_recordDate_description.title", "recordDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);

      ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PaymentItem model)
   {
      paymentNumber.textProperty().bindBidirectional(model.getPayment().paymentNumberProperty());
      paymentReceiptPrinted.textProperty().bindBidirectional(model.getPayment().paymentReceiptPrintedProperty(), new BooleanStringConverter());
      paymentMode.valueProperty().bindBidirectional(model.getPayment().paymentModeProperty());
      amount.numberProperty().bindBidirectional(model.getPayment().amountProperty());
      receivedAmount.numberProperty().bindBidirectional(model.getPayment().receivedAmountProperty());
      difference.numberProperty().bindBidirectional(model.getPayment().differenceProperty());
      paymentDate.calendarProperty().bindBidirectional(model.getPayment().paymentDateProperty());
      recordDate.calendarProperty().bindBidirectional(model.getPayment().recordDateProperty());
   }

   public void update(PaymentItemPayment data)
   {
      paymentNumber.textProperty().set(data.paymentNumberProperty().get());
      paymentReceiptPrinted.textProperty().set(new BooleanStringConverter().toString(data.paymentReceiptPrintedProperty().get()));
      paymentMode.valueProperty().set(data.paymentModeProperty().get());
      amount.numberProperty().set(data.amountProperty().get());
      receivedAmount.numberProperty().set(data.receivedAmountProperty().get());
      difference.numberProperty().set(data.differenceProperty().get());
      paymentDate.calendarProperty().set(data.paymentDateProperty().get());
      recordDate.calendarProperty().set(data.recordDateProperty().get());
   }

   public TextField getPaymentNumber()
   {
      return paymentNumber;
   }

   public CheckBox getPaymentReceiptPrinted()
   {
      return paymentReceiptPrinted;
   }

   public ComboBox<PaymentMode> getPaymentMode()
   {
      return paymentMode;
   }

   public BigDecimalField getAmount()
   {
      return amount;
   }

   public BigDecimalField getReceivedAmount()
   {
      return receivedAmount;
   }

   public BigDecimalField getDifference()
   {
      return difference;
   }

   public CalendarTextField getPaymentDate()
   {
      return paymentDate;
   }

   public CalendarTextField getRecordDate()
   {
      return recordDate;
   }
}
