package org.adorsys.adpharma.client.jpa.prescriber;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

@Singleton
public class PrescriberEditView
{

   AnchorPane rootPane;

   private Button saveButton;

   private Button cancelButton;

   @Inject
   private PrescriberView view;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class })
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

   public void bind(Prescriber model)
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

   public PrescriberView getView()
   {
      return view;
   }
}