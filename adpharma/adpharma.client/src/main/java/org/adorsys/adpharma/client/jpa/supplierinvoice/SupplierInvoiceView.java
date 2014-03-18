package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeListCellFatory;

public class SupplierInvoiceView extends AbstractForm<SupplierInvoice>
{

   private TextField invoiceNumber;

   private CheckBox settled;

   private ComboBox<InvoiceType> invoiceType;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountVAT;

   private BigDecimalField amountDiscount;

   private BigDecimalField amountAfterTax;

   private BigDecimalField netToPay;

   private BigDecimalField advancePayment;

   private BigDecimalField totalRestToPay;

   private CalendarTextField creationDate;

   @Inject
   private SupplierInvoiceInvoiceItemsForm supplierInvoiceInvoiceItemsForm;
   @Inject
   private SupplierInvoiceInvoiceItemsSelection supplierInvoiceInvoiceItemsSelection;

   @Inject
   private SupplierInvoiceSupplierForm supplierInvoiceSupplierForm;
   @Inject
   private SupplierInvoiceSupplierSelection supplierInvoiceSupplierSelection;

   @Inject
   private SupplierInvoiceCreatingUserForm supplierInvoiceCreatingUserForm;
   @Inject
   private SupplierInvoiceCreatingUserSelection supplierInvoiceCreatingUserSelection;

   @Inject
   private SupplierInvoiceAgencyForm supplierInvoiceAgencyForm;
   @Inject
   private SupplierInvoiceAgencySelection supplierInvoiceAgencySelection;

   @Inject
   private SupplierInvoiceDeliveryForm supplierInvoiceDeliveryForm;
   @Inject
   private SupplierInvoiceDeliverySelection supplierInvoiceDeliverySelection;

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

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      invoiceNumber = viewBuilder.addTextField("SupplierInvoice_invoiceNumber_description.title", "invoiceNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("SupplierInvoice_settled_description.title", "settled", resourceBundle);
      invoiceType = viewBuilder.addComboBox("SupplierInvoice_invoiceType_description.title", "invoiceType", resourceBundle, InvoiceType.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("SupplierInvoice_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      amountVAT = viewBuilder.addBigDecimalField("SupplierInvoice_amountVAT_description.title", "amountVAT", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("SupplierInvoice_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("SupplierInvoice_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      netToPay = viewBuilder.addBigDecimalField("SupplierInvoice_netToPay_description.title", "netToPay", resourceBundle, NumberType.CURRENCY, locale);
      advancePayment = viewBuilder.addBigDecimalField("SupplierInvoice_advancePayment_description.title", "advancePayment", resourceBundle, NumberType.CURRENCY, locale);
      totalRestToPay = viewBuilder.addBigDecimalField("SupplierInvoice_totalRestToPay_description.title", "totalRestToPay", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("SupplierInvoice_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("SupplierInvoice_invoiceItems_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoice_invoiceItems_description.title", "invoiceItems", resourceBundle, supplierInvoiceInvoiceItemsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("SupplierInvoice_invoiceItems_description.title", "invoiceItems", resourceBundle, supplierInvoiceInvoiceItemsSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SupplierInvoice_supplier_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoice_supplier_description.title", "supplier", resourceBundle, supplierInvoiceSupplierForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SupplierInvoice_supplier_description.title", "supplier", resourceBundle, supplierInvoiceSupplierSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SupplierInvoice_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoice_creatingUser_description.title", "creatingUser", resourceBundle, supplierInvoiceCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SupplierInvoice_creatingUser_description.title", "creatingUser", resourceBundle, supplierInvoiceCreatingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SupplierInvoice_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoice_agency_description.title", "agency", resourceBundle, supplierInvoiceAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SupplierInvoice_agency_description.title", "agency", resourceBundle, supplierInvoiceAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SupplierInvoice_delivery_description.title", resourceBundle);
      viewBuilder.addSubForm("SupplierInvoice_delivery_description.title", "delivery", resourceBundle, supplierInvoiceDeliveryForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SupplierInvoice_delivery_description.title", "delivery", resourceBundle, supplierInvoiceDeliverySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(invoiceType, invoiceTypeConverter, invoiceTypeListCellFatory, invoiceTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<SupplierInvoice>> validate(SupplierInvoice model)
   {
      Set<ConstraintViolation<SupplierInvoice>> violations = new HashSet<ConstraintViolation<SupplierInvoice>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(supplierInvoiceSupplierSelection.getSupplier(), model.getSupplier(), SupplierInvoice.class, "supplier", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(supplierInvoiceCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), SupplierInvoice.class, "creatingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(supplierInvoiceAgencySelection.getAgency(), model.getAgency(), SupplierInvoice.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(SupplierInvoice model)
   {
      invoiceNumber.textProperty().bindBidirectional(model.invoiceNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      invoiceType.valueProperty().bindBidirectional(model.invoiceTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
      amountVAT.numberProperty().bindBidirectional(model.amountVATProperty());
      amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
      netToPay.numberProperty().bindBidirectional(model.netToPayProperty());
      advancePayment.numberProperty().bindBidirectional(model.advancePaymentProperty());
      totalRestToPay.numberProperty().bindBidirectional(model.totalRestToPayProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      supplierInvoiceInvoiceItemsForm.bind(model);
      supplierInvoiceInvoiceItemsSelection.bind(model);
      supplierInvoiceSupplierForm.bind(model);
      supplierInvoiceSupplierSelection.bind(model);
      supplierInvoiceCreatingUserForm.bind(model);
      supplierInvoiceCreatingUserSelection.bind(model);
      supplierInvoiceAgencyForm.bind(model);
      supplierInvoiceAgencySelection.bind(model);
      supplierInvoiceDeliveryForm.bind(model);
      supplierInvoiceDeliverySelection.bind(model);
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

   public BigDecimalField getAmountVAT()
   {
      return amountVAT;
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

   public SupplierInvoiceInvoiceItemsForm getSupplierInvoiceInvoiceItemsForm()
   {
      return supplierInvoiceInvoiceItemsForm;
   }

   public SupplierInvoiceInvoiceItemsSelection getSupplierInvoiceInvoiceItemsSelection()
   {
      return supplierInvoiceInvoiceItemsSelection;
   }

   public SupplierInvoiceSupplierForm getSupplierInvoiceSupplierForm()
   {
      return supplierInvoiceSupplierForm;
   }

   public SupplierInvoiceSupplierSelection getSupplierInvoiceSupplierSelection()
   {
      return supplierInvoiceSupplierSelection;
   }

   public SupplierInvoiceCreatingUserForm getSupplierInvoiceCreatingUserForm()
   {
      return supplierInvoiceCreatingUserForm;
   }

   public SupplierInvoiceCreatingUserSelection getSupplierInvoiceCreatingUserSelection()
   {
      return supplierInvoiceCreatingUserSelection;
   }

   public SupplierInvoiceAgencyForm getSupplierInvoiceAgencyForm()
   {
      return supplierInvoiceAgencyForm;
   }

   public SupplierInvoiceAgencySelection getSupplierInvoiceAgencySelection()
   {
      return supplierInvoiceAgencySelection;
   }

   public SupplierInvoiceDeliveryForm getSupplierInvoiceDeliveryForm()
   {
      return supplierInvoiceDeliveryForm;
   }

   public SupplierInvoiceDeliverySelection getSupplierInvoiceDeliverySelection()
   {
      return supplierInvoiceDeliverySelection;
   }
}
