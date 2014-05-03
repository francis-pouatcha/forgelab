package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CustomerVoucherView extends AbstractForm<CustomerVoucher>
{

   private TextField voucherNumber;

   private CheckBox canceled;

   private CheckBox settled;

   private CheckBox voucherPrinted;

   private BigDecimalField amount;

   private BigDecimalField amountUsed;

   private BigDecimalField restAmount;

   private CalendarTextField modifiedDate;

   @Inject
   private CustomerVoucherCustomerInvoiceForm customerVoucherCustomerInvoiceForm;
   @Inject
   private CustomerVoucherCustomerInvoiceSelection customerVoucherCustomerInvoiceSelection;

   @Inject
   private CustomerVoucherCustomerForm customerVoucherCustomerForm;
   @Inject
   private CustomerVoucherCustomerSelection customerVoucherCustomerSelection;

   @Inject
   private CustomerVoucherAgencyForm customerVoucherAgencyForm;
   @Inject
   private CustomerVoucherAgencySelection customerVoucherAgencySelection;

   @Inject
   private CustomerVoucherRecordingUserForm customerVoucherRecordingUserForm;
   @Inject
   private CustomerVoucherRecordingUserSelection customerVoucherRecordingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      voucherNumber = viewBuilder.addTextField("CustomerVoucher_voucherNumber_description.title", "voucherNumber", resourceBundle);
      canceled = viewBuilder.addCheckBox("CustomerVoucher_canceled_description.title", "canceled", resourceBundle);
      settled = viewBuilder.addCheckBox("CustomerVoucher_settled_description.title", "settled", resourceBundle);
      voucherPrinted = viewBuilder.addCheckBox("CustomerVoucher_voucherPrinted_description.title", "voucherPrinted", resourceBundle);
      amount = viewBuilder.addBigDecimalField("CustomerVoucher_amount_description.title", "amount", resourceBundle, NumberType.CURRENCY, locale);
      amountUsed = viewBuilder.addBigDecimalField("CustomerVoucher_amountUsed_description.title", "amountUsed", resourceBundle, NumberType.CURRENCY, locale);
      restAmount = viewBuilder.addBigDecimalField("CustomerVoucher_restAmount_description.title", "restAmount", resourceBundle, NumberType.INTEGER, locale);
      modifiedDate = viewBuilder.addCalendarTextField("CustomerVoucher_modifiedDate_description.title", "modifiedDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("CustomerVoucher_customerInvoice_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerVoucher_customerInvoice_description.title", "customerInvoice", resourceBundle, customerVoucherCustomerInvoiceForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerVoucher_customerInvoice_description.title", "customerInvoice", resourceBundle, customerVoucherCustomerInvoiceSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerVoucher_customer_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerVoucher_customer_description.title", "customer", resourceBundle, customerVoucherCustomerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerVoucher_customer_description.title", "customer", resourceBundle, customerVoucherCustomerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerVoucher_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerVoucher_agency_description.title", "agency", resourceBundle, customerVoucherAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerVoucher_agency_description.title", "agency", resourceBundle, customerVoucherAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerVoucher_recordingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerVoucher_recordingUser_description.title", "recordingUser", resourceBundle, customerVoucherRecordingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerVoucher_recordingUser_description.title", "recordingUser", resourceBundle, customerVoucherRecordingUserSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      amount.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<CustomerVoucher>(bigDecimalFieldValidator, amount, CustomerVoucher.class, "amount", resourceBundle));
   }

   public Set<ConstraintViolation<CustomerVoucher>> validate(CustomerVoucher model)
   {
      Set<ConstraintViolation<CustomerVoucher>> violations = new HashSet<ConstraintViolation<CustomerVoucher>>();
      violations.addAll(bigDecimalFieldValidator.validate(amount, CustomerVoucher.class, "amount", resourceBundle));
      return violations;
   }

   public void bind(CustomerVoucher model)
   {
      voucherNumber.textProperty().bindBidirectional(model.voucherNumberProperty());
      canceled.textProperty().bindBidirectional(model.canceledProperty(), new BooleanStringConverter());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      voucherPrinted.textProperty().bindBidirectional(model.voucherPrintedProperty(), new BooleanStringConverter());
      amount.numberProperty().bindBidirectional(model.amountProperty());
      amountUsed.numberProperty().bindBidirectional(model.amountUsedProperty());
      restAmount.numberProperty().bindBidirectional(model.restAmountProperty());
      modifiedDate.calendarProperty().bindBidirectional(model.modifiedDateProperty());
      customerVoucherCustomerInvoiceForm.bind(model);
      customerVoucherCustomerInvoiceSelection.bind(model);
      customerVoucherCustomerForm.bind(model);
      customerVoucherCustomerSelection.bind(model);
      customerVoucherAgencyForm.bind(model);
      customerVoucherAgencySelection.bind(model);
      customerVoucherRecordingUserForm.bind(model);
      customerVoucherRecordingUserSelection.bind(model);
   }

   public TextField getVoucherNumber()
   {
      return voucherNumber;
   }

   public CheckBox getCanceled()
   {
      return canceled;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getVoucherPrinted()
   {
      return voucherPrinted;
   }

   public BigDecimalField getAmount()
   {
      return amount;
   }

   public BigDecimalField getAmountUsed()
   {
      return amountUsed;
   }

   public BigDecimalField getRestAmount()
   {
      return restAmount;
   }

   public CalendarTextField getModifiedDate()
   {
      return modifiedDate;
   }

   public CustomerVoucherCustomerInvoiceForm getCustomerVoucherCustomerInvoiceForm()
   {
      return customerVoucherCustomerInvoiceForm;
   }

   public CustomerVoucherCustomerInvoiceSelection getCustomerVoucherCustomerInvoiceSelection()
   {
      return customerVoucherCustomerInvoiceSelection;
   }

   public CustomerVoucherCustomerForm getCustomerVoucherCustomerForm()
   {
      return customerVoucherCustomerForm;
   }

   public CustomerVoucherCustomerSelection getCustomerVoucherCustomerSelection()
   {
      return customerVoucherCustomerSelection;
   }

   public CustomerVoucherAgencyForm getCustomerVoucherAgencyForm()
   {
      return customerVoucherAgencyForm;
   }

   public CustomerVoucherAgencySelection getCustomerVoucherAgencySelection()
   {
      return customerVoucherAgencySelection;
   }

   public CustomerVoucherRecordingUserForm getCustomerVoucherRecordingUserForm()
   {
      return customerVoucherRecordingUserForm;
   }

   public CustomerVoucherRecordingUserSelection getCustomerVoucherRecordingUserSelection()
   {
      return customerVoucherRecordingUserSelection;
   }
}
