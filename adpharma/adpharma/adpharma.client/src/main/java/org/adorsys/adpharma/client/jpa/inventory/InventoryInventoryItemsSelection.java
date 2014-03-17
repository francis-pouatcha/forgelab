package org.adorsys.adpharma.client.jpa.inventory;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemView;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;

public class InventoryInventoryItemsSelection extends AbstractSelection<Inventory, InventoryItem>
{

   /*
    * Selection popup
    */
   private VBox rootNode;
   private Button cancelButton;
   private Button confirmButton;

   /*
    * Dialog activation.
    */
   private Button addButton;
   private Button removeButton;

   @Inject
   private InventoryItemView view;

   Stage dialog;

   @Inject
   @Bundle({ CrudKeys.class, InventoryItem.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   protected InventoryItem targetEntity = new InventoryItem();

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder lazyViewBuilder = new LazyViewBuilder();
      HBox selectionButtonBar = lazyViewBuilder.addButtonBar();
      addButton = lazyViewBuilder.addButton(selectionButtonBar,
            "Entity_add.title", "addButton", resourceBundle);
      removeButton = lazyViewBuilder.addButton(selectionButtonBar,
            "Entity_remove.title", "removeButton", resourceBundle);
      gridRows = lazyViewBuilder.toRows();

      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addMainForm(view, ViewType.CREATE, false);
      viewBuilder.addSeparator();
      HBox buttonBar = viewBuilder.addButtonBar();
      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title",
            "cancelButton", resourceBundle);
      confirmButton = viewBuilder.addButton(buttonBar,
            "Entity_confirm.title", "confirmButton", resourceBundle);
      rootNode = new VBox();
      rootNode.getChildren().add(viewBuilder.toAnchorPane());

   }

   public void closeDialog()
   {
      if (dialog != null)
         dialog.close();
   }

   public void display()
   {
      if (dialog == null)
      {
         dialog = new Stage();
         dialog.initModality(Modality.WINDOW_MODAL);
         // Stage
         Scene scene = new Scene(rootNode);
         scene.getStylesheets().add("/styles/application.css");
         dialog.setScene(scene);
         dialog.setTitle(resourceBundle
               .getString("Inventory_inventoryItems_description.title"));
      }
      dialog.show();
   }

   public void bind(Inventory model)
   {
      view.bind(targetEntity);
   }

   public Button getCancelButton()
   {
      return cancelButton;
   }

   public Button getConfirmButton()
   {
      return confirmButton;
   }

   public Button getAddButton()
   {
      return addButton;
   }

   public Button getRemoveButton()
   {
      return removeButton;
   }

   public InventoryItem getTargetEntity()
   {
      return targetEntity;
   }
}
