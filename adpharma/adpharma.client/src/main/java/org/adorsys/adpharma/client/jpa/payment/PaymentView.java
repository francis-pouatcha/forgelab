package org.adorsys.adpharma.client.jpa.payment;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;

public class PaymentView extends AbstractForm<Payment>
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
   private PaymentAgencyForm paymentAgencyForm;
   @Inject
   private PaymentAgencySelection paymentAgencySelection;

   @Inject
   private PaymentCashierForm paymentCashierForm;
   @Inject
   private PaymentCashierSelection paymentCashierSelection;

   @Inject
   private PaymentCashDrawerForm paymentCashDrawerForm;
   @Inject
   private PaymentCashDrawerSelection paymentCashDrawerSelection;

   @Inject
   private PaymentInvoicesForm paymentInvoicesForm;
   @Inject
   private PaymentInvoicesSelection paymentInvoicesSelection;

   @Inject
   private PaymentPaidByForm paymentPaidByForm;
   @Inject
   private PaymentPaidBySelection paymentPaidBySelection;

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

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

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
      viewBuilder.addTitlePane("Payment_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("Payment_agency_description.title", "agency", resourceBundle, paymentAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Payment_agency_description.title", "agency", resourceBundle, paymentAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Payment_cashier_description.title", resourceBundle);
      viewBuilder.addSubForm("Payment_cashier_description.title", "cashier", resourceBundle, paymentCashierForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Payment_cashier_description.title", "cashier", resourceBundle, paymentCashierSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Payment_cashDrawer_description.title", resourceBundle);
      viewBuilder.addSubForm("Payment_cashDrawer_description.title", "cashDrawer", resourceBundle, paymentCashDrawerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Payment_cashDrawer_description.title", "cashDrawer", resourceBundle, paymentCashDrawerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Payment_invoices_description.title", resourceBundle);
      viewBuilder.addSubForm("Payment_invoices_description.title", "invoices", resourceBundle, paymentInvoicesForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("Payment_invoices_description.title", "invoices", resourceBundle, paymentInvoicesSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Payment_paidBy_description.title", resourceBundle);
      viewBuilder.addSubForm("Payment_paidBy_description.title", "paidBy", resourceBundle, paymentPaidByForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Payment_paidBy_description.title", "paidBy", resourceBundle, paymentPaidBySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<Payment>> validate(Payment model)
   {
      Set<ConstraintViolation<Payment>> violations = new HashSet<ConstraintViolation<Payment>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(paymentAgencySelection.getAgency(), model.getAgency(), Payment.class, "agency", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(paymentCashierSelection.getCashier(), model.getCashier(), Payment.class, "cashier", resourceBundle));
      return violations;
   }

   public void bind(Payment model)
   {
      paymentNumber.textProperty().bindBidirectional(model.paymentNumberProperty());
      paymentReceiptPrinted.textProperty().bindBidirectional(model.paymentReceiptPrintedProperty(), new BooleanStringConverter());
      paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());
      amount.numberProperty().bindBidirectional(model.amountProperty());
      receivedAmount.numberProperty().bindBidirectional(model.receivedAmountProperty());
      difference.numberProperty().bindBidirectional(model.differenceProperty());
      paymentDate.calendarProperty().bindBidirectional(model.paymentDateProperty());
      recordDate.calendarProperty().bindBidirectional(model.recordDateProperty());
      paymentAgencyForm.bind(model);
      paymentAgencySelection.bind(model);
      paymentCashierForm.bind(model);
      paymentCashierSelection.bind(model);
      paymentCashDrawerForm.bind(model);
      paymentCashDrawerSelection.bind(model);
      paymentInvoicesForm.bind(model);
      paymentInvoicesSelection.bind(model);
      paymentPaidByForm.bind(model);
      paymentPaidBySelection.bind(model);
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

   public PaymentAgencyForm getPaymentAgencyForm()
   {
      return paymentAgencyForm;
   }

   public PaymentAgencySelection getPaymentAgencySelection()
   {
      return paymentAgencySelection;
   }

   public PaymentCashierForm getPaymentCashierForm()
   {
      return paymentCashierForm;
   }

   public PaymentCashierSelection getPaymentCashierSelection()
   {
      return paymentCashierSelection;
   }

   public PaymentCashDrawerForm getPaymentCashDrawerForm()
   {
      return paymentCashDrawerForm;
   }

   public PaymentCashDrawerSelection getPaymentCashDrawerSelection()
   {
      return paymentCashDrawerSelection;
   }

   public PaymentInvoicesForm getPaymentInvoicesForm()
   {
      return paymentInvoicesForm;
   }

   public PaymentInvoicesSelection getPaymentInvoicesSelection()
   {
      return paymentInvoicesSelection;
   }

   public PaymentPaidByForm getPaymentPaidByForm()
   {
      return paymentPaidByForm;
   }

   public PaymentPaidBySelection getPaymentPaidBySelection()
   {
      return paymentPaidBySelection;
   }
}
