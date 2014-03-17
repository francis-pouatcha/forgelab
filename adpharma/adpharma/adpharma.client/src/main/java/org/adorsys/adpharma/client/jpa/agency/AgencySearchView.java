package org.adorsys.adpharma.client.jpa.agency;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javafx.crud.extensions.ViewModel;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;
import de.jensd.fx.fontawesome.AwesomeIcon;

import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.agency.Agency;

@Singleton
public class AgencySearchView
{

   private AnchorPane rootPane;

   private Button searchButton;

   private Button resetButton;

   private Button cancelButton;

   @Inject
   private AgencyViewSearchFields view;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addMainForm(view, ViewType.SEARCH, false);
      viewBuilder.addSeparator();
      HBox buttonBar = viewBuilder.addButtonBar();
      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
      resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
      rootPane = viewBuilder.toAnchorPane();

      searchButton.setDefaultButton(true);
   }

   public void bind(Agency model)
   {
      view.bind(model);
   }

   public AnchorPane getRootPane()
   {
      return rootPane;
   }

   public Button getSearchButton()
   {
      return searchButton;
   }

   public Button getResetButton()
   {
      return resetButton;
   }

   public Button getCancelButton()
   {
      return cancelButton;
   }

   public AgencyViewSearchFields getView()
   {
      return view;
   }
}
