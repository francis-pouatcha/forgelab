package org.adorsys.adpharma.client.jpa.salesorderitem;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;
import de.jensd.fx.fontawesome.AwesomeIcon;

import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

@Singleton
public class SalesOrderItemSearchView
{

   private AnchorPane rootPane;

   private Button searchButton;

   private Button resetButton;

   private Button cancelButton;

   @Inject
   private SalesOrderItemViewSearchFields view;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrderItem.class })
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

   public void bind(SalesOrderItem model)
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

   public SalesOrderItemViewSearchFields getView()
   {
      return view;
   }
}