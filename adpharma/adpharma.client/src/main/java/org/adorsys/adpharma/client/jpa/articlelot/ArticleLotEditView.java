package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

@Singleton
public class ArticleLotEditView
{

   AnchorPane rootPane;

   private Button saveButton;

   private Button cancelButton;

   @Inject
   private ArticleLotView view;

   @Inject
   @Bundle({ CrudKeys.class, ArticleLot.class })
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

   public void bind(ArticleLot model)
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

   public ArticleLotView getView()
   {
      return view;
   }
}
