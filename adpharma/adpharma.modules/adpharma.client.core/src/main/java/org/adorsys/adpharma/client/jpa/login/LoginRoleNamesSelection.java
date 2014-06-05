package org.adorsys.adpharma.client.jpa.login;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.rolename.RoleName;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

public class LoginRoleNamesSelection extends AbstractSelection<Login, RoleName>
{

   /*
    * Dialog activation.
    */
   private Button selectButton;

   /*
    * Selection popup. This popup offers two lists. One for the display of the list of actual assocciation entities and
    * one for the display of the list of target entities.
    */
   private VBox rootNode;
   private Button closeButton;
   private Button addButton;
   private Button removeButton;

   private TableView<RoleName> targetDataList;
   private Pagination targetPagination;

   private TableView<RoleName> assocDataList;
   private Pagination assocPagination;

   @Inject
   @Bundle({ CrudKeys.class
         , Login.class
         , RoleName.class
   })
   private ResourceBundle resourceBundle;

   Stage dialog;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder lazyViewBuilder = new LazyViewBuilder();
      selectButton = lazyViewBuilder.addButton(
            "Login_roleNames_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = lazyViewBuilder.toRows();

      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addTitlePane("Login_roleNames_description.title", resourceBundle);
      targetDataList = viewBuilder.addTable("targetDataList");
      viewBuilder.addStringColumn(targetDataList, "name", "RoleName_name_description.title", resourceBundle);
      targetPagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();
      HBox selectionButtonBar = viewBuilder.addButtonBar();
      addButton = viewBuilder.addButton(selectionButtonBar,
            "Entity_add.title", "addButton", resourceBundle);
      removeButton = viewBuilder.addButton(selectionButtonBar,
            "Entity_remove.title", "removeButton", resourceBundle);
      assocDataList = viewBuilder.addTable("assocDataList");
      viewBuilder.addStringColumn(assocDataList, "name", "RoleName_name_description.title", resourceBundle);
      assocPagination = viewBuilder.addPagination();
      HBox buttonBar = viewBuilder.addButtonBar();
      closeButton = viewBuilder.addButton(buttonBar, "Window_close.title", "closeButton", resourceBundle);
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
         dialog.setTitle(resourceBundle.getString("Login_roleNames_description.title"));
      }
      dialog.show();
   }

   public void bind(Login model)
   {
   }

   public Button getAddButton()
   {
      return addButton;
   }

   public Button getRemoveButton()
   {
      return removeButton;
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getCloseButton()
   {
      return closeButton;
   }

   public TableView<RoleName> getTargetDataList()
   {
      return targetDataList;
   }

   public TableView<RoleName> getAssocDataList()
   {
      return assocDataList;
   }

   public Pagination getTargetPagination()
   {
      return targetPagination;
   }

   public Pagination getAssocPagination()
   {
      return assocPagination;
   }
}
