package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomerForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomerSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsuranceForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsuranceSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUserForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUserSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgencyForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgencySelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrderForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrderSelection;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInvoiceItemsForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInvoiceItemsSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoicePaymentsForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsurance;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgency;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeListCellFatory;

public class CustomerVoucherCustomerInvoiceForm extends AbstractToOneAssociation<CustomerVoucher, CustomerInvoice>
{

   private TextField invoiceNumber;

   private CheckBox settled;

   private CheckBox cashed;

   private ComboBox<InvoiceType> invoiceType;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField taxAmount;

   private BigDecimalField amountDiscount;

   private BigDecimalField amountAfterTax;

   private BigDecimalField netToPay;

   private BigDecimalField customerRestTopay;

   private BigDecimalField insurranceRestTopay;

   private BigDecimalField advancePayment;

   private BigDecimalField totalRestToPay;

   private CalendarTextField creationDate;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(InvoiceType.class)
   private ResourceBundle invoiceTypeBundle;

   @Inject
   private InvoiceTypeConverter invoiceTypeConverter;

   @Inject
   private InvoiceTypeListCellFatory invoiceTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      invoiceNumber = viewBuilder.addTextField("CustomerInvoice_invoiceNumber_description.title", "invoiceNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("CustomerInvoice_settled_description.title", "settled", resourceBundle);
      cashed = viewBuilder.addCheckBox("CustomerInvoice_cashed_description.title", "cashed", resourceBundle);
      invoiceType = viewBuilder.addComboBox("CustomerInvoice_invoiceType_description.title", "invoiceType", resourceBundle, InvoiceType.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("CustomerInvoice_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      taxAmount = viewBuilder.addBigDecimalField("CustomerInvoice_taxAmount_description.title", "taxAmount", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("CustomerInvoice_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("CustomerInvoice_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      netToPay = viewBuilder.addBigDecimalField("CustomerInvoice_netToPay_description.title", "netToPay", resourceBundle, NumberType.CURRENCY, locale);
      customerRestTopay = viewBuilder.addBigDecimalField("CustomerInvoice_customerRestTopay_description.title", "customerRestTopay", resourceBundle, NumberType.CURRENCY, locale);
      insurranceRestTopay = viewBuilder.addBigDecimalField("CustomerInvoice_insurranceRestTopay_description.title", "insurranceRestTopay", resourceBundle, NumberType.CURRENCY, locale);
      advancePayment = viewBuilder.addBigDecimalField("CustomerInvoice_advancePayment_description.title", "advancePayment", resourceBundle, NumberType.CURRENCY, locale);
      totalRestToPay = viewBuilder.addBigDecimalField("CustomerInvoice_totalRestToPay_description.title", "totalRestToPay", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("CustomerInvoice_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);

      ComboBoxInitializer.initialize(invoiceType, invoiceTypeConverter, invoiceTypeListCellFatory, invoiceTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
      invoiceNumber.textProperty().bindBidirectional(model.getCustomerInvoice().invoiceNumberProperty());
      settled.textProperty().bindBidirectional(model.getCustomerInvoice().settledProperty(), new BooleanStringConverter());
      cashed.textProperty().bindBidirectional(model.getCustomerInvoice().cashedProperty(), new BooleanStringConverter());
      invoiceType.valueProperty().bindBidirectional(model.getCustomerInvoice().invoiceTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getCustomerInvoice().amountBeforeTaxProperty());
      taxAmount.numberProperty().bindBidirectional(model.getCustomerInvoice().taxAmountProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getCustomerInvoice().amountDiscountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getCustomerInvoice().amountAfterTaxProperty());
      netToPay.numberProperty().bindBidirectional(model.getCustomerInvoice().netToPayProperty());
      customerRestTopay.numberProperty().bindBidirectional(model.getCustomerInvoice().customerRestTopayProperty());
      insurranceRestTopay.numberProperty().bindBidirectional(model.getCustomerInvoice().insurranceRestTopayProperty());
      advancePayment.numberProperty().bindBidirectional(model.getCustomerInvoice().advancePaymentProperty());
      totalRestToPay.numberProperty().bindBidirectional(model.getCustomerInvoice().totalRestToPayProperty());
      creationDate.calendarProperty().bindBidirectional(model.getCustomerInvoice().creationDateProperty());
   }

   public void update(CustomerVoucherCustomerInvoice data)
   {
      invoiceNumber.textProperty().set(data.invoiceNumberProperty().get());
      settled.textProperty().set(new BooleanStringConverter().toString(data.settledProperty().get()));
      cashed.textProperty().set(new BooleanStringConverter().toString(data.cashedProperty().get()));
      invoiceType.valueProperty().set(data.invoiceTypeProperty().get());
      amountBeforeTax.numberProperty().set(data.amountBeforeTaxProperty().get());
      taxAmount.numberProperty().set(data.taxAmountProperty().get());
      amountDiscount.numberProperty().set(data.amountDiscountProperty().get());
      amountAfterTax.numberProperty().set(data.amountAfterTaxProperty().get());
      netToPay.numberProperty().set(data.netToPayProperty().get());
      customerRestTopay.numberProperty().set(data.customerRestTopayProperty().get());
      insurranceRestTopay.numberProperty().set(data.insurranceRestTopayProperty().get());
      advancePayment.numberProperty().set(data.advancePaymentProperty().get());
      totalRestToPay.numberProperty().set(data.totalRestToPayProperty().get());
      creationDate.calendarProperty().set(data.creationDateProperty().get());
   }

   public TextField getInvoiceNumber()
   {
      return invoiceNumber;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getCashed()
   {
      return cashed;
   }

   public ComboBox<InvoiceType> getInvoiceType()
   {
      return invoiceType;
   }

   public BigDecimalField getAmountBeforeTax()
   {
      return amountBeforeTax;
   }

   public BigDecimalField getTaxAmount()
   {
      return taxAmount;
   }

   public BigDecimalField getAmountDiscount()
   {
      return amountDiscount;
   }

   public BigDecimalField getAmountAfterTax()
   {
      return amountAfterTax;
   }

   public BigDecimalField getNetToPay()
   {
      return netToPay;
   }

   public BigDecimalField getCustomerRestTopay()
   {
      return customerRestTopay;
   }

   public BigDecimalField getInsurranceRestTopay()
   {
      return insurranceRestTopay;
   }

   public BigDecimalField getAdvancePayment()
   {
      return advancePayment;
   }

   public BigDecimalField getTotalRestToPay()
   {
      return totalRestToPay;
   }

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }
}
