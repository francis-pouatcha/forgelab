package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderView extends AbstractForm<SalesOrder>
{

   private TextField soNumber;

   private CheckBox cashed;

   private ComboBox<DocumentProcessingState> salesOrderStatus;

   private ComboBox<SalesOrderType> salesOrderType;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountVAT;

   private BigDecimalField amountDiscount;

   private BigDecimalField totalReturnAmount;

   private BigDecimalField amountAfterTax;

   private CalendarTextField creationDate;

   private CalendarTextField cancelationDate;

   private CalendarTextField restorationDate;

   @Inject
   private SalesOrderSalesOrderItemsForm salesOrderSalesOrderItemsForm;
   @Inject
   private SalesOrderSalesOrderItemsSelection salesOrderSalesOrderItemsSelection;

   @Inject
   private SalesOrderCashDrawerForm salesOrderCashDrawerForm;
   @Inject
   private SalesOrderCashDrawerSelection salesOrderCashDrawerSelection;

   @Inject
   private SalesOrderCustomerForm salesOrderCustomerForm;
   @Inject
   private SalesOrderCustomerSelection salesOrderCustomerSelection;

   @Inject
   private SalesOrderInsuranceForm salesOrderInsuranceForm;
   @Inject
   private SalesOrderInsuranceSelection salesOrderInsuranceSelection;

   @Inject
   private SalesOrderVatForm salesOrderVatForm;
   @Inject
   private SalesOrderVatSelection salesOrderVatSelection;

   @Inject
   private SalesOrderSalesAgentForm salesOrderSalesAgentForm;
   @Inject
   private SalesOrderSalesAgentSelection salesOrderSalesAgentSelection;

   @Inject
   private SalesOrderAgencyForm salesOrderAgencyForm;
   @Inject
   private SalesOrderAgencySelection salesOrderAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle salesOrderStatusBundle;

   @Inject
   private DocumentProcessingStateConverter salesOrderStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory salesOrderStatusListCellFatory;
   @Inject
   @Bundle(SalesOrderType.class)
   private ResourceBundle salesOrderTypeBundle;

   @Inject
   private SalesOrderTypeConverter salesOrderTypeConverter;

   @Inject
   private SalesOrderTypeListCellFatory salesOrderTypeListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      soNumber = viewBuilder.addTextField("SalesOrder_soNumber_description.title", "soNumber", resourceBundle);
      cashed = viewBuilder.addCheckBox("SalesOrder_cashed_description.title", "cashed", resourceBundle);
      salesOrderStatus = viewBuilder.addComboBox("SalesOrder_salesOrderStatus_description.title", "salesOrderStatus", resourceBundle, DocumentProcessingState.values());
      salesOrderType = viewBuilder.addComboBox("SalesOrder_salesOrderType_description.title", "salesOrderType", resourceBundle, SalesOrderType.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("SalesOrder_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.CURRENCY, locale);
      amountVAT = viewBuilder.addBigDecimalField("SalesOrder_amountVAT_description.title", "amountVAT", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("SalesOrder_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      totalReturnAmount = viewBuilder.addBigDecimalField("SalesOrder_totalReturnAmount_description.title", "totalReturnAmount", resourceBundle, NumberType.CURRENCY, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("SalesOrder_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("SalesOrder_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      cancelationDate = viewBuilder.addCalendarTextField("SalesOrder_cancelationDate_description.title", "cancelationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      restorationDate = viewBuilder.addCalendarTextField("SalesOrder_restorationDate_description.title", "restorationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("SalesOrder_salesOrderItems_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_salesOrderItems_description.title", "salesOrderItems", resourceBundle, salesOrderSalesOrderItemsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("SalesOrder_salesOrderItems_description.title", "salesOrderItems", resourceBundle, salesOrderSalesOrderItemsSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_cashDrawer_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_cashDrawer_description.title", "cashDrawer", resourceBundle, salesOrderCashDrawerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_cashDrawer_description.title", "cashDrawer", resourceBundle, salesOrderCashDrawerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_customer_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_customer_description.title", "customer", resourceBundle, salesOrderCustomerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_customer_description.title", "customer", resourceBundle, salesOrderCustomerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_insurance_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_insurance_description.title", "insurance", resourceBundle, salesOrderInsuranceForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_insurance_description.title", "insurance", resourceBundle, salesOrderInsuranceSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_vat_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_vat_description.title", "vat", resourceBundle, salesOrderVatForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_vat_description.title", "vat", resourceBundle, salesOrderVatSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_salesAgent_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_salesAgent_description.title", "salesAgent", resourceBundle, salesOrderSalesAgentForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_salesAgent_description.title", "salesAgent", resourceBundle, salesOrderSalesAgentSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("SalesOrder_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("SalesOrder_agency_description.title", "agency", resourceBundle, salesOrderAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("SalesOrder_agency_description.title", "agency", resourceBundle, salesOrderAgencySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(salesOrderStatus, salesOrderStatusConverter, salesOrderStatusListCellFatory, salesOrderStatusBundle);
      ComboBoxInitializer.initialize(salesOrderType, salesOrderTypeConverter, salesOrderTypeListCellFatory, salesOrderTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<SalesOrder>> validate(SalesOrder model)
   {
      Set<ConstraintViolation<SalesOrder>> violations = new HashSet<ConstraintViolation<SalesOrder>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(salesOrderCustomerSelection.getCustomer(), model.getCustomer(), SalesOrder.class, "customer", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(salesOrderSalesAgentSelection.getSalesAgent(), model.getSalesAgent(), SalesOrder.class, "salesAgent", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(salesOrderAgencySelection.getAgency(), model.getAgency(), SalesOrder.class, "agency", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(salesOrderCashDrawerSelection.getCashDrawer(), model.getCashDrawer(), SalesOrder.class, "cashDrawer", resourceBundle));

      return violations;
   }

   public void bind(SalesOrder model)
   {
      soNumber.textProperty().bindBidirectional(model.soNumberProperty());
      cashed.textProperty().bindBidirectional(model.cashedProperty(), new BooleanStringConverter());
      salesOrderStatus.valueProperty().bindBidirectional(model.salesOrderStatusProperty());
      salesOrderType.valueProperty().bindBidirectional(model.salesOrderTypeProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
      amountVAT.numberProperty().bindBidirectional(model.amountVATProperty());
      amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
      totalReturnAmount.numberProperty().bindBidirectional(model.totalReturnAmountProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      cancelationDate.calendarProperty().bindBidirectional(model.cancelationDateProperty());
      restorationDate.calendarProperty().bindBidirectional(model.restorationDateProperty());
      salesOrderSalesOrderItemsForm.bind(model);
      salesOrderSalesOrderItemsSelection.bind(model);
      salesOrderCashDrawerForm.bind(model);
      salesOrderCashDrawerSelection.bind(model);
      salesOrderCustomerForm.bind(model);
      salesOrderCustomerSelection.bind(model);
      salesOrderInsuranceForm.bind(model);
      salesOrderInsuranceSelection.bind(model);
      salesOrderVatForm.bind(model);
      salesOrderVatSelection.bind(model);
      salesOrderSalesAgentForm.bind(model);
      salesOrderSalesAgentSelection.bind(model);
      salesOrderAgencyForm.bind(model);
      salesOrderAgencySelection.bind(model);
   }

   public TextField getSoNumber()
   {
      return soNumber;
   }

   public CheckBox getCashed()
   {
      return cashed;
   }

   public ComboBox<DocumentProcessingState> getSalesOrderStatus()
   {
      return salesOrderStatus;
   }

   public ComboBox<SalesOrderType> getSalesOrderType()
   {
      return salesOrderType;
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

   public BigDecimalField getTotalReturnAmount()
   {
      return totalReturnAmount;
   }

   public BigDecimalField getAmountAfterTax()
   {
      return amountAfterTax;
   }

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }

   public CalendarTextField getCancelationDate()
   {
      return cancelationDate;
   }

   public CalendarTextField getRestorationDate()
   {
      return restorationDate;
   }

   public SalesOrderSalesOrderItemsForm getSalesOrderSalesOrderItemsForm()
   {
      return salesOrderSalesOrderItemsForm;
   }

   public SalesOrderSalesOrderItemsSelection getSalesOrderSalesOrderItemsSelection()
   {
      return salesOrderSalesOrderItemsSelection;
   }

   public SalesOrderCashDrawerForm getSalesOrderCashDrawerForm()
   {
      return salesOrderCashDrawerForm;
   }

   public SalesOrderCashDrawerSelection getSalesOrderCashDrawerSelection()
   {
      return salesOrderCashDrawerSelection;
   }

   public SalesOrderCustomerForm getSalesOrderCustomerForm()
   {
      return salesOrderCustomerForm;
   }

   public SalesOrderCustomerSelection getSalesOrderCustomerSelection()
   {
      return salesOrderCustomerSelection;
   }

   public SalesOrderInsuranceForm getSalesOrderInsuranceForm()
   {
      return salesOrderInsuranceForm;
   }

   public SalesOrderInsuranceSelection getSalesOrderInsuranceSelection()
   {
      return salesOrderInsuranceSelection;
   }

   public SalesOrderVatForm getSalesOrderVatForm()
   {
      return salesOrderVatForm;
   }

   public SalesOrderVatSelection getSalesOrderVatSelection()
   {
      return salesOrderVatSelection;
   }

   public SalesOrderSalesAgentForm getSalesOrderSalesAgentForm()
   {
      return salesOrderSalesAgentForm;
   }

   public SalesOrderSalesAgentSelection getSalesOrderSalesAgentSelection()
   {
      return salesOrderSalesAgentSelection;
   }

   public SalesOrderAgencyForm getSalesOrderAgencyForm()
   {
      return salesOrderAgencyForm;
   }

   public SalesOrderAgencySelection getSalesOrderAgencySelection()
   {
      return salesOrderAgencySelection;
   }
}
