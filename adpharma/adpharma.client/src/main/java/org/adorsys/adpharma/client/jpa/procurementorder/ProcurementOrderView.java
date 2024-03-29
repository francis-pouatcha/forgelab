package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeConverter;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerModeListCellFatory;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderType;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderTypeConverter;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderTypeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderView extends AbstractForm<ProcurementOrder>
{

   private TextField procurementOrderNumber;

   private ComboBox<ProcmtOrderTriggerMode> procmtOrderTriggerMode;

   private ComboBox<ProcurementOrderType> procurementOrderType;

   private ComboBox<DocumentProcessingState> poStatus;

   private BigDecimalField amountBeforeTax;

   private BigDecimalField amountAfterTax;

   private BigDecimalField amountDiscount;

   private BigDecimalField taxAmount;

   private BigDecimalField netAmountToPay;

   private CalendarTextField submissionDate;

   private CalendarTextField createdDate;

   @Inject
   private ProcurementOrderProcurementOrderItemsForm procurementOrderProcurementOrderItemsForm;
   @Inject
   private ProcurementOrderProcurementOrderItemsSelection procurementOrderProcurementOrderItemsSelection;

   @Inject
   private ProcurementOrderCreatingUserForm procurementOrderCreatingUserForm;
   @Inject
   private ProcurementOrderCreatingUserSelection procurementOrderCreatingUserSelection;

   @Inject
   private ProcurementOrderSupplierForm procurementOrderSupplierForm;
   @Inject
   private ProcurementOrderSupplierSelection procurementOrderSupplierSelection;

   @Inject
   private ProcurementOrderAgencyForm procurementOrderAgencyForm;
   @Inject
   private ProcurementOrderAgencySelection procurementOrderAgencySelection;

   @Inject
   private ProcurementOrderVatForm procurementOrderVatForm;
   @Inject
   private ProcurementOrderVatSelection procurementOrderVatSelection;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(ProcmtOrderTriggerMode.class)
   private ResourceBundle procmtOrderTriggerModeBundle;

   @Inject
   private ProcmtOrderTriggerModeConverter procmtOrderTriggerModeConverter;

   @Inject
   private ProcmtOrderTriggerModeListCellFatory procmtOrderTriggerModeListCellFatory;
   @Inject
   @Bundle(ProcurementOrderType.class)
   private ResourceBundle procurementOrderTypeBundle;

   @Inject
   private ProcurementOrderTypeConverter procurementOrderTypeConverter;

   @Inject
   private ProcurementOrderTypeListCellFatory procurementOrderTypeListCellFatory;
   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle poStatusBundle;

   @Inject
   private DocumentProcessingStateConverter poStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory poStatusListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;
   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      procurementOrderNumber = viewBuilder.addTextField("ProcurementOrder_procurementOrderNumber_description.title", "procurementOrderNumber", resourceBundle);
      procmtOrderTriggerMode = viewBuilder.addComboBox("ProcurementOrder_procmtOrderTriggerMode_description.title", "procmtOrderTriggerMode", resourceBundle, ProcmtOrderTriggerMode.values());
      procurementOrderType = viewBuilder.addComboBox("ProcurementOrder_procurementOrderType_description.title", "procurementOrderType", resourceBundle, ProcurementOrderType.values());
      poStatus = viewBuilder.addComboBox("ProcurementOrder_poStatus_description.title", "poStatus", resourceBundle, DocumentProcessingState.values());
      amountBeforeTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountBeforeTax_description.title", "amountBeforeTax", resourceBundle, NumberType.INTEGER, locale);
      amountAfterTax = viewBuilder.addBigDecimalField("ProcurementOrder_amountAfterTax_description.title", "amountAfterTax", resourceBundle, NumberType.CURRENCY, locale);
      amountDiscount = viewBuilder.addBigDecimalField("ProcurementOrder_amountDiscount_description.title", "amountDiscount", resourceBundle, NumberType.CURRENCY, locale);
      taxAmount = viewBuilder.addBigDecimalField("ProcurementOrder_taxAmount_description.title", "taxAmount", resourceBundle, NumberType.CURRENCY, locale);
      netAmountToPay = viewBuilder.addBigDecimalField("ProcurementOrder_netAmountToPay_description.title", "netAmountToPay", resourceBundle, NumberType.CURRENCY, locale);
      submissionDate = viewBuilder.addCalendarTextField("ProcurementOrder_submissionDate_description.title", "submissionDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      createdDate = viewBuilder.addCalendarTextField("ProcurementOrder_createdDate_description.title", "createdDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("ProcurementOrder_procurementOrderItems_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrder_procurementOrderItems_description.title", "procurementOrderItems", resourceBundle, procurementOrderProcurementOrderItemsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("ProcurementOrder_procurementOrderItems_description.title", "procurementOrderItems", resourceBundle, procurementOrderProcurementOrderItemsSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProcurementOrder_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrder_creatingUser_description.title", "creatingUser", resourceBundle, procurementOrderCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrder_creatingUser_description.title", "creatingUser", resourceBundle, procurementOrderCreatingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProcurementOrder_supplier_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrder_supplier_description.title", "supplier", resourceBundle, procurementOrderSupplierForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrder_supplier_description.title", "supplier", resourceBundle, procurementOrderSupplierSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProcurementOrder_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrder_agency_description.title", "agency", resourceBundle, procurementOrderAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrder_agency_description.title", "agency", resourceBundle, procurementOrderAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProcurementOrder_vat_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrder_vat_description.title", "vat", resourceBundle, procurementOrderVatForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrder_vat_description.title", "vat", resourceBundle, procurementOrderVatSelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(procmtOrderTriggerMode, procmtOrderTriggerModeConverter, procmtOrderTriggerModeListCellFatory, procmtOrderTriggerModeBundle);
      ComboBoxInitializer.initialize(procurementOrderType, procurementOrderTypeConverter, procurementOrderTypeListCellFatory, procurementOrderTypeBundle);
      ComboBoxInitializer.initialize(poStatus, poStatusConverter, poStatusListCellFatory, poStatusBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      procurementOrderNumber.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProcurementOrder>(textInputControlValidator, procurementOrderNumber, ProcurementOrder.class, "procurementOrderNumber", resourceBundle));
      amountBeforeTax.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<ProcurementOrder>(bigDecimalFieldValidator, amountBeforeTax, ProcurementOrder.class, "amountBeforeTax", resourceBundle));
      // no active validator
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<ProcurementOrder>> validate(ProcurementOrder model)
   {
      Set<ConstraintViolation<ProcurementOrder>> violations = new HashSet<ConstraintViolation<ProcurementOrder>>();
      violations.addAll(textInputControlValidator.validate(procurementOrderNumber, ProcurementOrder.class, "procurementOrderNumber", resourceBundle));
      violations.addAll(bigDecimalFieldValidator.validate(amountBeforeTax, ProcurementOrder.class, "amountBeforeTax", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(procurementOrderCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), ProcurementOrder.class, "creatingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(procurementOrderSupplierSelection.getSupplier(), model.getSupplier(), ProcurementOrder.class, "supplier", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(procurementOrderAgencySelection.getAgency(), model.getAgency(), ProcurementOrder.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(ProcurementOrder model)
   {
      procurementOrderNumber.textProperty().bindBidirectional(model.procurementOrderNumberProperty());
      procmtOrderTriggerMode.valueProperty().bindBidirectional(model.procmtOrderTriggerModeProperty());
      procurementOrderType.valueProperty().bindBidirectional(model.procurementOrderTypeProperty());
      poStatus.valueProperty().bindBidirectional(model.poStatusProperty());
      amountBeforeTax.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
      amountAfterTax.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
      amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
      taxAmount.numberProperty().bindBidirectional(model.taxAmountProperty());
      netAmountToPay.numberProperty().bindBidirectional(model.netAmountToPayProperty());
      submissionDate.calendarProperty().bindBidirectional(model.submissionDateProperty());
      createdDate.calendarProperty().bindBidirectional(model.createdDateProperty());
      procurementOrderProcurementOrderItemsForm.bind(model);
      procurementOrderProcurementOrderItemsSelection.bind(model);
      procurementOrderCreatingUserForm.bind(model);
      procurementOrderCreatingUserSelection.bind(model);
      procurementOrderSupplierForm.bind(model);
      procurementOrderSupplierSelection.bind(model);
      procurementOrderAgencyForm.bind(model);
      procurementOrderAgencySelection.bind(model);
      procurementOrderVatForm.bind(model);
      procurementOrderVatSelection.bind(model);
   }

   public TextField getProcurementOrderNumber()
   {
      return procurementOrderNumber;
   }

   public ComboBox<ProcmtOrderTriggerMode> getProcmtOrderTriggerMode()
   {
      return procmtOrderTriggerMode;
   }

   public ComboBox<ProcurementOrderType> getProcurementOrderType()
   {
      return procurementOrderType;
   }

   public ComboBox<DocumentProcessingState> getPoStatus()
   {
      return poStatus;
   }

   public BigDecimalField getAmountBeforeTax()
   {
      return amountBeforeTax;
   }

   public BigDecimalField getAmountAfterTax()
   {
      return amountAfterTax;
   }

   public BigDecimalField getAmountDiscount()
   {
      return amountDiscount;
   }

   public BigDecimalField getTaxAmount()
   {
      return taxAmount;
   }

   public BigDecimalField getNetAmountToPay()
   {
      return netAmountToPay;
   }

   public CalendarTextField getSubmissionDate()
   {
      return submissionDate;
   }

   public CalendarTextField getCreatedDate()
   {
      return createdDate;
   }

   public ProcurementOrderProcurementOrderItemsForm getProcurementOrderProcurementOrderItemsForm()
   {
      return procurementOrderProcurementOrderItemsForm;
   }

   public ProcurementOrderProcurementOrderItemsSelection getProcurementOrderProcurementOrderItemsSelection()
   {
      return procurementOrderProcurementOrderItemsSelection;
   }

   public ProcurementOrderCreatingUserForm getProcurementOrderCreatingUserForm()
   {
      return procurementOrderCreatingUserForm;
   }

   public ProcurementOrderCreatingUserSelection getProcurementOrderCreatingUserSelection()
   {
      return procurementOrderCreatingUserSelection;
   }

   public ProcurementOrderSupplierForm getProcurementOrderSupplierForm()
   {
      return procurementOrderSupplierForm;
   }

   public ProcurementOrderSupplierSelection getProcurementOrderSupplierSelection()
   {
      return procurementOrderSupplierSelection;
   }

   public ProcurementOrderAgencyForm getProcurementOrderAgencyForm()
   {
      return procurementOrderAgencyForm;
   }

   public ProcurementOrderAgencySelection getProcurementOrderAgencySelection()
   {
      return procurementOrderAgencySelection;
   }

   public ProcurementOrderVatForm getProcurementOrderVatForm()
   {
      return procurementOrderVatForm;
   }

   public ProcurementOrderVatSelection getProcurementOrderVatSelection()
   {
      return procurementOrderVatSelection;
   }
}
