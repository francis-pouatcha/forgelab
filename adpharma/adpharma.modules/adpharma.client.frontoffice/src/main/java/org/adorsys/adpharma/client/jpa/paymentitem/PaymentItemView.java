package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PaymentItemView extends AbstractForm<PaymentItem>
{

   private TextField documentNumber;

   private TextField documentDetails;

   private ComboBox<PaymentMode> paymentMode;

   private BigDecimalField amount;

   private BigDecimalField receivedAmount;

   @Inject
   private PaymentItemPaymentForm paymentItemPaymentForm;

   @Inject
   private PaymentItemPaidByForm paymentItemPaidByForm;
   @Inject
   private PaymentItemPaidBySelection paymentItemPaidBySelection;

   @Inject
   @Bundle({ CrudKeys.class, PaymentItem.class })
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
      documentNumber = viewBuilder.addTextField("PaymentItem_documentNumber_description.title", "documentNumber", resourceBundle);
      documentDetails = viewBuilder.addTextField("PaymentItem_documentDetails_description.title", "documentDetails", resourceBundle);
      paymentMode = viewBuilder.addComboBox("PaymentItem_paymentMode_description.title", "paymentMode", resourceBundle, PaymentMode.values());
      amount = viewBuilder.addBigDecimalField("PaymentItem_amount_description.title", "amount", resourceBundle, NumberType.CURRENCY, locale);
      receivedAmount = viewBuilder.addBigDecimalField("PaymentItem_receivedAmount_description.title", "receivedAmount", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addTitlePane("PaymentItem_payment_description.title", resourceBundle);
      viewBuilder.addSubForm("PaymentItem_payment_description.title", "payment", resourceBundle, paymentItemPaymentForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("PaymentItem_paidBy_description.title", resourceBundle);
      viewBuilder.addSubForm("PaymentItem_paidBy_description.title", "paidBy", resourceBundle, paymentItemPaidByForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("PaymentItem_paidBy_description.title", "paidBy", resourceBundle, paymentItemPaidBySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<PaymentItem>> validate(PaymentItem model)
   {
      Set<ConstraintViolation<PaymentItem>> violations = new HashSet<ConstraintViolation<PaymentItem>>();
      return violations;
   }

   public void bind(PaymentItem model)
   {
      documentNumber.textProperty().bindBidirectional(model.documentNumberProperty());
      documentDetails.textProperty().bindBidirectional(model.documentDetailsProperty());
      paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());
      amount.numberProperty().bindBidirectional(model.amountProperty());
      receivedAmount.numberProperty().bindBidirectional(model.receivedAmountProperty());
      paymentItemPaymentForm.bind(model);
      paymentItemPaidByForm.bind(model);
      paymentItemPaidBySelection.bind(model);
   }

   public TextField getDocumentNumber()
   {
      return documentNumber;
   }

   public TextField getDocumentDetails()
   {
      return documentDetails;
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

   public PaymentItemPaymentForm getPaymentItemPaymentForm()
   {
      return paymentItemPaymentForm;
   }

   public PaymentItemPaidByForm getPaymentItemPaidByForm()
   {
      return paymentItemPaidByForm;
   }

   public PaymentItemPaidBySelection getPaymentItemPaidBySelection()
   {
      return paymentItemPaidBySelection;
   }
}
