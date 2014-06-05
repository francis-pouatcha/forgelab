package org.adorsys.adpharma.client.jpa.inventory;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventoryView extends AbstractForm<Inventory>
{

   private TextField inventoryNumber;

   private TextArea description;

   private ComboBox<DocumentProcessingState> inventoryStatus;

   private BigDecimalField gapSaleAmount;

   private BigDecimalField gapPurchaseAmount;

   private CalendarTextField inventoryDate;

   @Inject
   private InventoryInventoryItemsForm inventoryInventoryItemsForm;
   @Inject
   private InventoryInventoryItemsSelection inventoryInventoryItemsSelection;

   @Inject
   private InventoryRecordingUserForm inventoryRecordingUserForm;
   @Inject
   private InventoryRecordingUserSelection inventoryRecordingUserSelection;

   @Inject
   private InventoryAgencyForm inventoryAgencyForm;
   @Inject
   private InventoryAgencySelection inventoryAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle inventoryStatusBundle;

   @Inject
   private DocumentProcessingStateConverter inventoryStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory inventoryStatusListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      inventoryNumber = viewBuilder.addTextField("Inventory_inventoryNumber_description.title", "inventoryNumber", resourceBundle);
      description = viewBuilder.addTextArea("Inventory_description_description.title", "description", resourceBundle);
      inventoryStatus = viewBuilder.addComboBox("Inventory_inventoryStatus_description.title", "inventoryStatus", resourceBundle, DocumentProcessingState.values());
      gapSaleAmount = viewBuilder.addBigDecimalField("Inventory_gapSaleAmount_description.title", "gapSaleAmount", resourceBundle, NumberType.CURRENCY, locale);
      gapPurchaseAmount = viewBuilder.addBigDecimalField("Inventory_gapPurchaseAmount_description.title", "gapPurchaseAmount", resourceBundle, NumberType.CURRENCY, locale);
      inventoryDate = viewBuilder.addCalendarTextField("Inventory_inventoryDate_description.title", "inventoryDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("Inventory_inventoryItems_description.title", resourceBundle);
      viewBuilder.addSubForm("Inventory_inventoryItems_description.title", "inventoryItems", resourceBundle, inventoryInventoryItemsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("Inventory_inventoryItems_description.title", "inventoryItems", resourceBundle, inventoryInventoryItemsSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Inventory_recordingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("Inventory_recordingUser_description.title", "recordingUser", resourceBundle, inventoryRecordingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Inventory_recordingUser_description.title", "recordingUser", resourceBundle, inventoryRecordingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Inventory_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("Inventory_agency_description.title", "agency", resourceBundle, inventoryAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Inventory_agency_description.title", "agency", resourceBundle, inventoryAgencySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(inventoryStatus, inventoryStatusConverter, inventoryStatusListCellFatory, inventoryStatusBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      description.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Inventory>(textInputControlValidator, description, Inventory.class, "description", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<Inventory>> validate(Inventory model)
   {
      Set<ConstraintViolation<Inventory>> violations = new HashSet<ConstraintViolation<Inventory>>();
      violations.addAll(textInputControlValidator.validate(description, Inventory.class, "description", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(inventoryRecordingUserSelection.getRecordingUser(), model.getRecordingUser(), Inventory.class, "recordingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(inventoryAgencySelection.getAgency(), model.getAgency(), Inventory.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(Inventory model)
   {
      inventoryNumber.textProperty().bindBidirectional(model.inventoryNumberProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());
      inventoryStatus.valueProperty().bindBidirectional(model.inventoryStatusProperty());
      gapSaleAmount.numberProperty().bindBidirectional(model.gapSaleAmountProperty());
      gapPurchaseAmount.numberProperty().bindBidirectional(model.gapPurchaseAmountProperty());
      inventoryDate.calendarProperty().bindBidirectional(model.inventoryDateProperty());
      inventoryInventoryItemsForm.bind(model);
      inventoryInventoryItemsSelection.bind(model);
      inventoryRecordingUserForm.bind(model);
      inventoryRecordingUserSelection.bind(model);
      inventoryAgencyForm.bind(model);
      inventoryAgencySelection.bind(model);
   }

   public TextField getInventoryNumber()
   {
      return inventoryNumber;
   }

   public TextArea getDescription()
   {
      return description;
   }

   public ComboBox<DocumentProcessingState> getInventoryStatus()
   {
      return inventoryStatus;
   }

   public BigDecimalField getGapSaleAmount()
   {
      return gapSaleAmount;
   }

   public BigDecimalField getGapPurchaseAmount()
   {
      return gapPurchaseAmount;
   }

   public CalendarTextField getInventoryDate()
   {
      return inventoryDate;
   }

   public InventoryInventoryItemsForm getInventoryInventoryItemsForm()
   {
      return inventoryInventoryItemsForm;
   }

   public InventoryInventoryItemsSelection getInventoryInventoryItemsSelection()
   {
      return inventoryInventoryItemsSelection;
   }

   public InventoryRecordingUserForm getInventoryRecordingUserForm()
   {
      return inventoryRecordingUserForm;
   }

   public InventoryRecordingUserSelection getInventoryRecordingUserSelection()
   {
      return inventoryRecordingUserSelection;
   }

   public InventoryAgencyForm getInventoryAgencyForm()
   {
      return inventoryAgencyForm;
   }

   public InventoryAgencySelection getInventoryAgencySelection()
   {
      return inventoryAgencySelection;
   }
}
