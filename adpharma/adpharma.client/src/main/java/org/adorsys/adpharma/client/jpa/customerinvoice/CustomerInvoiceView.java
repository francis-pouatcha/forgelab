package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import org.adorsys.adpharma.client.jpa.payment.Payment;

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
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeListCellFatory;

public class CustomerInvoiceView extends AbstractForm<CustomerInvoice>
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
   private CustomerInvoiceInvoiceItemsForm customerInvoiceInvoiceItemsForm;
   @Inject
   private CustomerInvoiceInvoiceItemsSelection customerInvoiceInvoiceItemsSelection;

   @Inject
   private CustomerInvoiceCustomerForm customerInvoiceCustomerForm;
   @Inject
   private CustomerInvoiceCustomerSelection customerInvoiceCustomerSelection;

   @Inject
   private CustomerInvoiceInsuranceForm customerInvoiceInsuranceForm;
   @Inject
   private CustomerInvoiceInsuranceSelection customerInvoiceInsuranceSelection;

   @Inject
   private CustomerInvoiceCreatingUserForm customerInvoiceCreatingUserForm;
   @Inject
   private CustomerInvoiceCreatingUserSelection customerInvoiceCreatingUserSelection;

   @Inject
   private CustomerInvoiceAgencyForm customerInvoiceAgencyForm;
   @Inject
   private CustomerInvoiceAgencySelection customerInvoiceAgencySelection;

   @Inject
   private CustomerInvoiceSalesOrderForm customerInvoiceSalesOrderForm;
   @Inject
   private CustomerInvoiceSalesOrderSelection customerInvoiceSalesOrderSelection;

   @Inject
   private CustomerInvoicePaymentsForm customerInvoicePaymentsForm;

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

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

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
      viewBuilder.addTitlePane("CustomerInvoice_invoiceItems_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_invoiceItems_description.title", "invoiceItems", resourceBundle, customerInvoiceInvoiceItemsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("CustomerInvoice_invoiceItems_description.title", "invoiceItems", resourceBundle, customerInvoiceInvoiceItemsSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_customer_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_customer_description.title", "customer", resourceBundle, customerInvoiceCustomerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoice_customer_description.title", "customer", resourceBundle, customerInvoiceCustomerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_insurance_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_insurance_description.title", "insurance", resourceBundle, customerInvoiceInsuranceForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoice_insurance_description.title", "insurance", resourceBundle, customerInvoiceInsuranceSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_creatingUser_description.title", "creatingUser", resourceBundle, customerInvoiceCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoice_creatingUser_description.title", "creatingUser", resourceBundle, customerInvoiceCreatingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_agency_description.title", "agency", resourceBundle, customerInvoiceAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoice_agency_description.title", "agency", resourceBundle, customerInvoiceAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_salesOrder_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_salesOrder_description.title", "salesOrder", resourceBundle, customerInvoiceSalesOrderForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("CustomerInvoice_salesOrder_description.title", "salesOrder", resourceBundle, customerInvoiceSalesOrderSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("CustomerInvoice_payments_description.title", resourceBundle);
      viewBuilder.addSubForm("CustomerInvoice_payments_description.title", "payments", resourceBundle, customerInvoicePaymentsForm, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(invoiceType, invoiceTypeConverter, invoiceTypeListCellFatory, invoiceTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<CustomerInvoice>> validate(CustomerInvoice model)
   {
      Set<ConstraintViolation<CustomerInvoice>> violations = new HashSet<ConstraintViolation<CustomerInvoice>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(customerInvoiceCustomerSelection.getCustomer(), model.getCustomer(), CustomerInvoice.class, "customer", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(customerInvoiceCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), CustomerInvoice.class, "creatingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(customerInvoiceAgencySelection.getAgency(), model.getAgency(), CustomerInvoice.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(CustomerInvoice model)
   {
      invoiceNumber.textProperty().bindBidirectional(model.invoiceNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      cashed.textProperty().bindBidirectional(model.cashedProperty(), new BooleanStringConverter());
      invoiceType.valueProperty().bindBidirectional(model.invoiceTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
      taxAmount.numberProperty().bindBidirectional(model.taxAmountProperty());
      amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
      netToPay.numberProperty().bindBidirectional(model.netToPayProperty());
      customerRestTopay.numberProperty().bindBidirectional(model.customerRestTopayProperty());
      insurranceRestTopay.numberProperty().bindBidirectional(model.insurranceRestTopayProperty());
      advancePayment.numberProperty().bindBidirectional(model.advancePaymentProperty());
      totalRestToPay.numberProperty().bindBidirectional(model.totalRestToPayProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      customerInvoiceInvoiceItemsForm.bind(model);
      customerInvoiceInvoiceItemsSelection.bind(model);
      customerInvoiceCustomerForm.bind(model);
      customerInvoiceCustomerSelection.bind(model);
      customerInvoiceInsuranceForm.bind(model);
      customerInvoiceInsuranceSelection.bind(model);
      customerInvoiceCreatingUserForm.bind(model);
      customerInvoiceCreatingUserSelection.bind(model);
      customerInvoiceAgencyForm.bind(model);
      customerInvoiceAgencySelection.bind(model);
      customerInvoiceSalesOrderForm.bind(model);
      customerInvoiceSalesOrderSelection.bind(model);
      customerInvoicePaymentsForm.bind(model);
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

   public CustomerInvoiceInvoiceItemsForm getCustomerInvoiceInvoiceItemsForm()
   {
      return customerInvoiceInvoiceItemsForm;
   }

   public CustomerInvoiceInvoiceItemsSelection getCustomerInvoiceInvoiceItemsSelection()
   {
      return customerInvoiceInvoiceItemsSelection;
   }

   public CustomerInvoiceCustomerForm getCustomerInvoiceCustomerForm()
   {
      return customerInvoiceCustomerForm;
   }

   public CustomerInvoiceCustomerSelection getCustomerInvoiceCustomerSelection()
   {
      return customerInvoiceCustomerSelection;
   }

   public CustomerInvoiceInsuranceForm getCustomerInvoiceInsuranceForm()
   {
      return customerInvoiceInsuranceForm;
   }

   public CustomerInvoiceInsuranceSelection getCustomerInvoiceInsuranceSelection()
   {
      return customerInvoiceInsuranceSelection;
   }

   public CustomerInvoiceCreatingUserForm getCustomerInvoiceCreatingUserForm()
   {
      return customerInvoiceCreatingUserForm;
   }

   public CustomerInvoiceCreatingUserSelection getCustomerInvoiceCreatingUserSelection()
   {
      return customerInvoiceCreatingUserSelection;
   }

   public CustomerInvoiceAgencyForm getCustomerInvoiceAgencyForm()
   {
      return customerInvoiceAgencyForm;
   }

   public CustomerInvoiceAgencySelection getCustomerInvoiceAgencySelection()
   {
      return customerInvoiceAgencySelection;
   }

   public CustomerInvoiceSalesOrderForm getCustomerInvoiceSalesOrderForm()
   {
      return customerInvoiceSalesOrderForm;
   }

   public CustomerInvoiceSalesOrderSelection getCustomerInvoiceSalesOrderSelection()
   {
      return customerInvoiceSalesOrderSelection;
   }

   public CustomerInvoicePaymentsForm getCustomerInvoicePaymentsForm()
   {
      return customerInvoicePaymentsForm;
   }
}
