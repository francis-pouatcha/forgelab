package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.inventory.InventoryRecordingUserForm;
import org.adorsys.adpharma.client.jpa.inventory.InventoryRecordingUserSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.ComboBox;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.inventory.InventoryAgencyForm;
import org.adorsys.adpharma.client.jpa.inventory.InventoryAgencySelection;
import org.adorsys.adpharma.client.jpa.inventory.InventoryInventoryItemsForm;
import org.adorsys.adpharma.client.jpa.inventory.InventoryInventoryItemsSelection;
import org.adorsys.adpharma.client.jpa.inventory.InventoryAgency;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

public class InventoryItemInventoryForm extends AbstractToOneAssociation<InventoryItem, Inventory>
{

   private TextField inventoryNumber;

   private ComboBox<DocumentProcessingState> inventoryStatus;

   private BigDecimalField gapSaleAmount;

   private BigDecimalField gapPurchaseAmount;

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

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      inventoryNumber = viewBuilder.addTextField("Inventory_inventoryNumber_description.title", "inventoryNumber", resourceBundle);
      inventoryStatus = viewBuilder.addComboBox("Inventory_inventoryStatus_description.title", "inventoryStatus", resourceBundle, DocumentProcessingState.values());
      gapSaleAmount = viewBuilder.addBigDecimalField("Inventory_gapSaleAmount_description.title", "gapSaleAmount", resourceBundle, NumberType.CURRENCY, locale);
      gapPurchaseAmount = viewBuilder.addBigDecimalField("Inventory_gapPurchaseAmount_description.title", "gapPurchaseAmount", resourceBundle, NumberType.CURRENCY, locale);

      ComboBoxInitializer.initialize(inventoryStatus, inventoryStatusConverter, inventoryStatusListCellFatory, inventoryStatusBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(InventoryItem model)
   {
      inventoryNumber.textProperty().bindBidirectional(model.getInventory().inventoryNumberProperty());
      inventoryStatus.valueProperty().bindBidirectional(model.getInventory().inventoryStatusProperty());
      gapSaleAmount.numberProperty().bindBidirectional(model.getInventory().gapSaleAmountProperty());
      gapPurchaseAmount.numberProperty().bindBidirectional(model.getInventory().gapPurchaseAmountProperty());
   }

   public TextField getInventoryNumber()
   {
      return inventoryNumber;
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
}
