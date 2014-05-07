package org.adorsys.adpharma.client.jpa.inventory;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
