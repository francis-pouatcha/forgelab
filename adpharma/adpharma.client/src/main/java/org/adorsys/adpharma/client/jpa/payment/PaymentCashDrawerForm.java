package org.adorsys.adpharma.client.jpa.payment;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerCashierForm;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerCashierSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerClosedByForm;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerClosedBySelection;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgencyForm;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgencySelection;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerAgency;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class PaymentCashDrawerForm extends AbstractToOneAssociation<Payment, CashDrawer>
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
   @Bundle({ CrudKeys.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

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

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      cashDrawerNumber.textProperty().bindBidirectional(model.getCashDrawer().cashDrawerNumberProperty());
      opened.textProperty().bindBidirectional(model.getCashDrawer().openedProperty(), new BooleanStringConverter());
      initialAmount.numberProperty().bindBidirectional(model.getCashDrawer().initialAmountProperty());
      totalCashIn.numberProperty().bindBidirectional(model.getCashDrawer().totalCashInProperty());
      totalCashOut.numberProperty().bindBidirectional(model.getCashDrawer().totalCashOutProperty());
      totalCash.numberProperty().bindBidirectional(model.getCashDrawer().totalCashProperty());
      totalCheck.numberProperty().bindBidirectional(model.getCashDrawer().totalCheckProperty());
      totalCreditCard.numberProperty().bindBidirectional(model.getCashDrawer().totalCreditCardProperty());
      totalCompanyVoucher.numberProperty().bindBidirectional(model.getCashDrawer().totalCompanyVoucherProperty());
      totalClientVoucher.numberProperty().bindBidirectional(model.getCashDrawer().totalClientVoucherProperty());
      openingDate.calendarProperty().bindBidirectional(model.getCashDrawer().openingDateProperty());
      closingDate.calendarProperty().bindBidirectional(model.getCashDrawer().closingDateProperty());
   }

   public void update(PaymentCashDrawer data)
   {
      cashDrawerNumber.textProperty().set(data.cashDrawerNumberProperty().get());
      opened.textProperty().set(new BooleanStringConverter().toString(data.openedProperty().get()));
      initialAmount.numberProperty().set(data.initialAmountProperty().get());
      totalCashIn.numberProperty().set(data.totalCashInProperty().get());
      totalCashOut.numberProperty().set(data.totalCashOutProperty().get());
      totalCash.numberProperty().set(data.totalCashProperty().get());
      totalCheck.numberProperty().set(data.totalCheckProperty().get());
      totalCreditCard.numberProperty().set(data.totalCreditCardProperty().get());
      totalCompanyVoucher.numberProperty().set(data.totalCompanyVoucherProperty().get());
      totalClientVoucher.numberProperty().set(data.totalClientVoucherProperty().get());
      openingDate.calendarProperty().set(data.openingDateProperty().get());
      closingDate.calendarProperty().set(data.closingDateProperty().get());
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
}
