package org.adorsys.adpharma.client.jpa.employer;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
import org.adorsys.adpharma.client.jpa.employer.Employer;

@Singleton
public class EmployerCreateView
{

   private AnchorPane rootPane;

   private Button saveButton;

   private Button resetButton;

   private Button searchButton;

   @Inject
   private EmployerView view;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class })
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

   public void bind(Employer model)
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

   public EmployerView getView()
   {
      return view;
   }
}
