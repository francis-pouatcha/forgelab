package org.adorsys.adpharma.client.jpa.permissionname;

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
public class PermissionNameCreateView
{

   private AnchorPane rootPane;

   private Button saveButton;

   private Button resetButton;

   private Button searchButton;

   @Inject
   private PermissionNameView view;

   @Inject
   @Bundle({ CrudKeys.class, PermissionName.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addMainForm(view, ViewType.CREATE, false);
      viewBuilder.addSeparator();
      HBox buttonBar = viewBuilder.addButtonBar();
      saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
      resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
      rootPane = viewBuilder.toAnchorPane();
   }

   public void bind(PermissionName model)
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

   public Button getResetButton()
   {
      return resetButton;
   }

   public Button getSearchButton()
   {
      return searchButton;
   }

   public PermissionNameView getView()
   {
      return view;
   }
}
