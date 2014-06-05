package org.adorsys.adpharma.client.jpa.documentarchive;

import java.util.List;
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
public class DocumentArchiveDisplayView
{

   private AnchorPane rootPane;

   private Button editButton;

   private Button removeButton;

   private Button searchButton;

   private HBox buttonBarLeft;

   private Button confirmSelectionButton;

   @Inject
   private DocumentArchiveView view;

   @Inject
   @Bundle({ CrudKeys.class, DocumentArchive.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addMainForm(view, ViewType.DISPLAY, true);
      viewBuilder.addSeparator();
      List<HBox> doubleButtonBar = viewBuilder.addDoubleButtonBar();
      buttonBarLeft = doubleButtonBar.get(0);
      confirmSelectionButton = viewBuilder.addButton(buttonBarLeft, "Entity_select.title", "confirmSelectionButton", resourceBundle);
      HBox buttonBarRight = doubleButtonBar.get(1);
      editButton = viewBuilder.addButton(buttonBarRight, "Entity_edit.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
      removeButton = viewBuilder.addButton(buttonBarRight, "Entity_remove.title", "removeButton", resourceBundle, AwesomeIcon.TRASH_ALT);
      searchButton = viewBuilder.addButton(buttonBarRight, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
      rootPane = viewBuilder.toAnchorPane();
   }

   public void bind(DocumentArchive model)
   {
      view.bind(model);
   }

   public AnchorPane getRootPane()
   {
      return rootPane;
   }

   public Button getEditButton()
   {
      return editButton;
   }

   public Button getRemoveButton()
   {
      return removeButton;
   }

   public Button getSearchButton()
   {
      return searchButton;
   }

   public DocumentArchiveView getView()
   {
      return view;
   }

   public Button getConfirmSelectionButton()
   {
      return confirmSelectionButton;
   }
}
