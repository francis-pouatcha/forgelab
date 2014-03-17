package org.adorsys.adpharma.client.jpa.payment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;

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
