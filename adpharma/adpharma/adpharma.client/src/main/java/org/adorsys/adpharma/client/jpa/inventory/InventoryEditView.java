package org.adorsys.adpharma.client.jpa.inventory;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;

@Singleton
public class InventoryEditView
{

   AnchorPane rootPane;

   private Button saveButton;

   private Button cancelButton;

   @Inject
   private InventoryView view;

   @Inject
   @Bundle({ CrudKeys.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addMainForm(view, ViewType.EDIT, false);
      viewBuilder.addSeparator();
      HBox buttonBar = viewBuilder.addButtonBar();
      saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
      rootPane = viewBuilder.toAnchorPane();
   }

   public void bind(Inventory model)
   {
      view.bind(model);
   }

   public AnchorPane getRootPane()
   {
      return rootPane;
   }

   public Button getSaveButton()
   {
      return saveButton;
   }

   public Button getCancelButton()
   {
      return cancelButton;
   }

   public InventoryView getView()
   {
      return view;
   }
}
