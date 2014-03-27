package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
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
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerView extends AbstractForm<CashDrawer>
{

   private TextField cashDrawerNumber;

   private CheckBox opened;

   private BigDecimalField initialAmount;

   private BigDecimalField totalCashIn;

   private BigDecimalField totalCashOut;

   private BigDecimalField totalCash;

   private BigDecimalField totalCheck;

   private BigDecimalField totalCreditCard;

   private BigDecimalField totalCompanyVoucher;

   private BigDecimalField totalClientVoucher;

   private CalendarTextField openingDate;

   private CalendarTextField closingDate;

   @Inject
   private CashDrawerCashierForm cashDrawerCashierForm;
   @Inject
   private CashDrawerCashierSelection cashDrawerCashierSelection;

   @Inject
   private CashDrawerClosedByForm cashDrawerClosedByForm;
   @Inject
   private CashDrawerClosedBySelection cashDrawerClosedBySelection;

   @Inject
   private CashDrawerAgencyForm cashDrawerAgencyForm;
   @Inject
   private CashDrawerAgencySelection cashDrawerAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      cashDrawerNumber = viewBuilder.addTextField("CashDrawer_cashDrawerNumber_description.title", "cashDrawerNumber", resourceBundle);
       opened = viewBuilder.addCheckBox("CashDrawer_opened_description.title", "opened", resourceBundle);
      initialAmount = viewBuilder.addBigDecimalField("CashDrawer_initialAmount_description.title", "initialAmount", resourceBundle, NumberType.CURRENCY, locale);
      totalCashIn = viewBuilder.addBigDecimalField("CashDrawer_totalCashIn_description.title", "totalCashIn", resourceBundle, NumberType.CURRENCY, locale);
      totalCashOut = viewBuilder.addBigDecimalField("CashDrawer_totalCashOut_description.title", "totalCashOut", resourceBundle, NumberType.CURRENCY, locale);
      totalCash = viewBuilder.addBigDecimalField("CashDrawer_totalCash_description.title", "totalCash", resourceBundle, NumberType.CURRENCY, locale);
      totalCheck = viewBuilder.addBigDecimalField("CashDrawer_totalCheck_description.title", "totalCheck", resourceBundle, NumberType.CURRENCY, locale);
      totalCreditCard = viewBuilder.addBigDecimalField("CashDrawer_totalCreditCard_description.title", "totalCreditCard", resourceBundle, NumberType.CURRENCY, locale);
      totalCompanyVoucher = viewBuilder.addBigDecimalField("CashDrawer_totalCompanyVoucher_description.title", "totalCompanyVoucher", resourceBundle, NumberType.CURRENCY, locale);
      totalClientVoucher = viewBuilder.addBigDecimalField("CashDrawer_totalClientVoucher_description.title", "totalClientVoucher", resourceBundle, NumberType.CURRENCY, locale);
      openingDate = viewBuilder.addCalendarTextField("CashDrawer_openingDate_description.title", "openingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      closingDate = viewBuilder.addCalendarTextField("CashDrawer_closingDate_description.title", "closingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("CashDrawer_cashier_description.title", resourceBundle);
      viewBuilder.addSubForm("CashDrawer_cashier_description.title", "cashier", resourceBundle, cashDrawerCashierForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CashDrawer_cashier_description.title", "cashier", resourceBundle, cashDrawerCashierSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CashDrawer_closedBy_description.title", resourceBundle);
      viewBuilder.addSubForm("CashDrawer_closedBy_description.title", "closedBy", resourceBundle, cashDrawerClosedByForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CashDrawer_closedBy_description.title", "closedBy", resourceBundle, cashDrawerClosedBySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CashDrawer_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("CashDrawer_agency_description.title", "agency", resourceBundle, cashDrawerAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CashDrawer_agency_description.title", "agency", resourceBundle, cashDrawerAgencySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<CashDrawer>> validate(CashDrawer model)
   {
      Set<ConstraintViolation<CashDrawer>> violations = new HashSet<ConstraintViolation<CashDrawer>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(cashDrawerCashierSelection.getCashier(), model.getCashier(), CashDrawer.class, "cashier", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(cashDrawerAgencySelection.getAgency(), model.getAgency(), CashDrawer.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(CashDrawer model)
   {
      cashDrawerNumber.textProperty().bindBidirectional(model.cashDrawerNumberProperty());
      opened.textProperty().bindBidirectional(model.openedProperty(), new BooleanStringConverter());
      initialAmount.numberProperty().bindBidirectional(model.initialAmountProperty());
      totalCashIn.numberProperty().bindBidirectional(model.totalCashInProperty());
      totalCashOut.numberProperty().bindBidirectional(model.totalCashOutProperty());
      totalCash.numberProperty().bindBidirectional(model.totalCashProperty());
      totalCheck.numberProperty().bindBidirectional(model.totalCheckProperty());
      totalCreditCard.numberProperty().bindBidirectional(model.totalCreditCardProperty());
      totalCompanyVoucher.numberProperty().bindBidirectional(model.totalCompanyVoucherProperty());
      totalClientVoucher.numberProperty().bindBidirectional(model.totalClientVoucherProperty());
      openingDate.calendarProperty().bindBidirectional(model.openingDateProperty());
      closingDate.calendarProperty().bindBidirectional(model.closingDateProperty());
      cashDrawerCashierForm.bind(model);
      cashDrawerCashierSelection.bind(model);
      cashDrawerClosedByForm.bind(model);
      cashDrawerClosedBySelection.bind(model);
      cashDrawerAgencyForm.bind(model);
      cashDrawerAgencySelection.bind(model);
   }

   public TextField getCashDrawerNumber()
   {
      return cashDrawerNumber;
   }

   public CheckBox getOpened()
   {
      return opened;
   }

   public BigDecimalField getInitialAmount()
   {
      return initialAmount;
   }

   public BigDecimalField getTotalCashIn()
   {
      return totalCashIn;
   }

   public BigDecimalField getTotalCashOut()
   {
      return totalCashOut;
   }

   public BigDecimalField getTotalCash()
   {
      return totalCash;
   }

   public BigDecimalField getTotalCheck()
   {
      return totalCheck;
   }

   public BigDecimalField getTotalCreditCard()
   {
      return totalCreditCard;
   }

   public BigDecimalField getTotalCompanyVoucher()
   {
      return totalCompanyVoucher;
   }

   public BigDecimalField getTotalClientVoucher()
   {
      return totalClientVoucher;
   }

   public CalendarTextField getOpeningDate()
   {
      return openingDate;
   }

   public CalendarTextField getClosingDate()
   {
      return closingDate;
   }

   public CashDrawerCashierForm getCashDrawerCashierForm()
   {
      return cashDrawerCashierForm;
   }

   public CashDrawerCashierSelection getCashDrawerCashierSelection()
   {
      return cashDrawerCashierSelection;
   }

   public CashDrawerClosedByForm getCashDrawerClosedByForm()
   {
      return cashDrawerClosedByForm;
   }

   public CashDrawerClosedBySelection getCashDrawerClosedBySelection()
   {
      return cashDrawerClosedBySelection;
   }

   public CashDrawerAgencyForm getCashDrawerAgencyForm()
   {
      return cashDrawerAgencyForm;
   }

   public CashDrawerAgencySelection getCashDrawerAgencySelection()
   {
      return cashDrawerAgencySelection;
   }
}
