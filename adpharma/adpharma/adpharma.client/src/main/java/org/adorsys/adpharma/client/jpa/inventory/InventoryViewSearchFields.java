package org.adorsys.adpharma.client.jpa.inventory;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.TextField;
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
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

public class InventoryViewSearchFields extends AbstractForm<Inventory>
{

   private TextField inventoryNumber;

   private TextArea description;

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
      description = viewBuilder.addTextArea("Inventory_description_description.title", "description", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
      inventoryNumber.textProperty().bindBidirectional(model.inventoryNumberProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());

   }

   public TextField getInventoryNumber()
   {
      return inventoryNumber;
   }

   public TextArea getDescription()
   {
      return description;
   }
}
