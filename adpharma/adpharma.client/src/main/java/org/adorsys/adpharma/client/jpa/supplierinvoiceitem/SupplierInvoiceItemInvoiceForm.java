package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceSupplierForm;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceSupplierSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceCreatingUserForm;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceCreatingUserSelection;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceAgencyForm;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceAgencySelection;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceDeliveryForm;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceDeliverySelection;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceInvoiceItemsForm;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceInvoiceItemsSelection;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceSupplier;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceAgency;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceDelivery;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeListCellFatory;

public class SupplierInvoiceItemInvoiceForm extends AbstractToOneAssociation<SupplierInvoiceItem, SupplierInvoice>
{

   private TextField invoiceNumber;

   private CheckBox settled;

   private ComboBox<InvoiceType> invoiceType;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField taxAmount;

   private BigDecimalField amountDiscount;

   private BigDecimalField amountAfterTax;

   private BigDecimalField netToPay;

   private BigDecimalField advancePayment;

   private BigDecimalField totalRestToPay;

   private CalendarTextField creationDate;

   @Inject
   @Bundle({ CrudKeys.class, SupplierInvoice.class })
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
      invoiceNumber = viewBuilder.addTextField("SupplierInvoice_invoiceNumber_description.title", "invoiceNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("SupplierInvoice_settled_description.title", "settled", resourceBundle);
      invoiceType = viewBuilder.addComboBox("SupplierInvoice_invoiceType_description.title", "invoiceType", resourceBundle, InvoiceType.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("SupplierInvoice_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      taxAmount = viewBuilder.addBigDecimalField("SupplierInvoice_taxAmount_description.title", "taxAmount", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("SupplierInvoice_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("SupplierInvoice_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      netToPay = viewBuilder.addBigDecimalField("SupplierInvoice_netToPay_description.title", "netToPay", resourceBundle, NumberType.CURRENCY, locale);
      advancePayment = viewBuilder.addBigDecimalField("SupplierInvoice_advancePayment_description.title", "advancePayment", resourceBundle, NumberType.CURRENCY, locale);
      totalRestToPay = viewBuilder.addBigDecimalField("SupplierInvoice_totalRestToPay_description.title", "totalRestToPay", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("SupplierInvoice_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);

      ComboBoxInitializer.initialize(invoiceType, invoiceTypeConverter, invoiceTypeListCellFatory, invoiceTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoiceItem model)
   {
      invoiceNumber.textProperty().bindBidirectional(model.getInvoice().invoiceNumberProperty());
      settled.textProperty().bindBidirectional(model.getInvoice().settledProperty(), new BooleanStringConverter());
      invoiceType.valueProperty().bindBidirectional(model.getInvoice().invoiceTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.getInvoice().amountBeforeTaxProperty());
      taxAmount.numberProperty().bindBidirectional(model.getInvoice().taxAmountProperty());
      amountDiscount.numberProperty().bindBidirectional(model.getInvoice().amountDiscountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.getInvoice().amountAfterTaxProperty());
      netToPay.numberProperty().bindBidirectional(model.getInvoice().netToPayProperty());
      advancePayment.numberProperty().bindBidirectional(model.getInvoice().advancePaymentProperty());
      totalRestToPay.numberProperty().bindBidirectional(model.getInvoice().totalRestToPayProperty());
      creationDate.calendarProperty().bindBidirectional(model.getInvoice().creationDateProperty());
   }

   public void update(SupplierInvoiceItemInvoice data)
   {
      invoiceNumber.textProperty().set(data.invoiceNumberProperty().get());
      settled.textProperty().set(new BooleanStringConverter().toString(data.settledProperty().get()));
      invoiceType.valueProperty().set(data.invoiceTypeProperty().get());
      amountBeforeTax.numberProperty().set(data.amountBeforeTaxProperty().get());
      taxAmount.numberProperty().set(data.taxAmountProperty().get());
      amountDiscount.numberProperty().set(data.amountDiscountProperty().get());
      amountAfterTax.numberProperty().set(data.amountAfterTaxProperty().get());
      netToPay.numberProperty().set(data.netToPayProperty().get());
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
