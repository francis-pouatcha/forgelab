package org.adorsys.adpharma.client.jpa.debtstatement;

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
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DebtStatementView extends AbstractForm<DebtStatement>
{

   private TextField statementNumber;

   private CheckBox settled;

   private CheckBox canceled;

   private CheckBox useVoucher;

   private BigDecimalField initialAmount;

   private BigDecimalField advancePayment;

   private BigDecimalField restAmount;

   private BigDecimalField amountFromVouchers;

   private CalendarTextField paymentDate;

   @Inject
   private DebtStatementInsurranceForm debtStatementInsurranceForm;
   @Inject
   private DebtStatementInsurranceSelection debtStatementInsurranceSelection;

   @Inject
   private DebtStatementAgencyForm debtStatementAgencyForm;
   @Inject
   private DebtStatementAgencySelection debtStatementAgencySelection;

   @Inject
   private DebtStatementInvoicesForm debtStatementInvoicesForm;

   @Inject
   @Bundle({ CrudKeys.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      statementNumber = viewBuilder.addTextField("DebtStatement_statementNumber_description.title", "statementNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("DebtStatement_settled_description.title", "settled", resourceBundle);
      canceled = viewBuilder.addCheckBox("DebtStatement_canceled_description.title", "canceled", resourceBundle);
      useVoucher = viewBuilder.addCheckBox("DebtStatement_useVoucher_description.title", "useVoucher", resourceBundle);
      initialAmount = viewBuilder.addBigDecimalField("DebtStatement_initialAmount_description.title", "initialAmount", resourceBundle, NumberType.CURRENCY, locale);
      advancePayment = viewBuilder.addBigDecimalField("DebtStatement_advancePayment_description.title", "advancePayment", resourceBundle, NumberType.CURRENCY, locale);
      restAmount = viewBuilder.addBigDecimalField("DebtStatement_restAmount_description.title", "restAmount", resourceBundle, NumberType.CURRENCY, locale);
      amountFromVouchers = viewBuilder.addBigDecimalField("DebtStatement_amountFromVouchers_description.title", "amountFromVouchers", resourceBundle, NumberType.CURRENCY, locale);
      paymentDate = viewBuilder.addCalendarTextField("DebtStatement_paymentDate_description.title", "paymentDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("DebtStatement_insurrance_description.title", resourceBundle);
      viewBuilder.addSubForm("DebtStatement_insurrance_description.title", "insurrance", resourceBundle, debtStatementInsurranceForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DebtStatement_insurrance_description.title", "insurrance", resourceBundle, debtStatementInsurranceSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("DebtStatement_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("DebtStatement_agency_description.title", "agency", resourceBundle, debtStatementAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DebtStatement_agency_description.title", "agency", resourceBundle, debtStatementAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("DebtStatement_invoices_description.title", resourceBundle);
      viewBuilder.addSubForm("DebtStatement_invoices_description.title", "invoices", resourceBundle, debtStatementInvoicesForm, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
   }

   public Set<ConstraintViolation<DebtStatement>> validate(DebtStatement model)
   {
      Set<ConstraintViolation<DebtStatement>> violations = new HashSet<ConstraintViolation<DebtStatement>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(debtStatementAgencySelection.getAgency(), model.getAgency(), DebtStatement.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(DebtStatement model)
   {
      statementNumber.textProperty().bindBidirectional(model.statementNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      canceled.textProperty().bindBidirectional(model.canceledProperty(), new BooleanStringConverter());
      useVoucher.textProperty().bindBidirectional(model.useVoucherProperty(), new BooleanStringConverter());
      initialAmount.numberProperty().bindBidirectional(model.initialAmountProperty());
      advancePayment.numberProperty().bindBidirectional(model.advancePaymentProperty());
      restAmount.numberProperty().bindBidirectional(model.restAmountProperty());
      amountFromVouchers.numberProperty().bindBidirectional(model.amountFromVouchersProperty());
      paymentDate.calendarProperty().bindBidirectional(model.paymentDateProperty());
      debtStatementInsurranceForm.bind(model);
      debtStatementInsurranceSelection.bind(model);
      debtStatementAgencyForm.bind(model);
      debtStatementAgencySelection.bind(model);
      debtStatementInvoicesForm.bind(model);
   }

   public TextField getStatementNumber()
   {
      return statementNumber;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getCanceled()
   {
      return canceled;
   }

   public CheckBox getUseVoucher()
   {
      return useVoucher;
   }

   public BigDecimalField getInitialAmount()
   {
      return initialAmount;
   }

   public BigDecimalField getAdvancePayment()
   {
      return advancePayment;
   }

   public BigDecimalField getRestAmount()
   {
      return restAmount;
   }

   public BigDecimalField getAmountFromVouchers()
   {
      return amountFromVouchers;
   }

   public CalendarTextField getPaymentDate()
   {
      return paymentDate;
   }

   public DebtStatementInsurranceForm getDebtStatementInsurranceForm()
   {
      return debtStatementInsurranceForm;
   }

   public DebtStatementInsurranceSelection getDebtStatementInsurranceSelection()
   {
      return debtStatementInsurranceSelection;
   }

   public DebtStatementAgencyForm getDebtStatementAgencyForm()
   {
      return debtStatementAgencyForm;
   }

   public DebtStatementAgencySelection getDebtStatementAgencySelection()
   {
      return debtStatementAgencySelection;
   }

   public DebtStatementInvoicesForm getDebtStatementInvoicesForm()
   {
      return debtStatementInvoicesForm;
   }
}
