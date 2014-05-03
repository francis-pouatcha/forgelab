package org.adorsys.adpharma.client.jpa.payment;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PaymentViewSearchFields extends AbstractForm<Payment>
{

   private TextField paymentNumber;

   private CheckBox paymentReceiptPrinted;

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

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      paymentNumber.textProperty().bindBidirectional(model.paymentNumberProperty());
      paymentReceiptPrinted.textProperty().bindBidirectional(model.paymentReceiptPrintedProperty(), new BooleanStringConverter());

   }

   public TextField getPaymentNumber()
   {
      return paymentNumber;
   }

   public CheckBox getPaymentReceiptPrinted()
   {
      return paymentReceiptPrinted;
   }
}
