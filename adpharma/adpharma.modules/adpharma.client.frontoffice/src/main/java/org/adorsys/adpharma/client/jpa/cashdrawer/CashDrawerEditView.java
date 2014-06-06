package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

@Singleton
public class CashDrawerEditView
{

   AnchorPane rootPane;

   private Button saveButton;

   private Button cancelButton;

   @Inject
   private CashDrawerView view;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class })
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

   public void bind(CashDrawer model)
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

   public CashDrawerView getView()
   {
      return view;
   }
}
