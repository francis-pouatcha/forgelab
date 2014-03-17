package org.adorsys.adpharma.client.jpa.vat;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
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
import org.adorsys.adpharma.client.jpa.vat.VAT;

@Singleton
public class VATSearchView
{

   private AnchorPane rootPane;

   private Button searchButton;

   private Button resetButton;

   private Button cancelButton;

   @Inject
   private VATViewSearchFields view;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class })
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

   public void bind(VAT model)
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

   public VATViewSearchFields getView()
   {
      return view;
   }
}
