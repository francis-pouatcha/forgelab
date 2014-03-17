package org.adorsys.adpharma.client.jpa.permissionname;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnum;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxValidator;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxFoccusChangedListener;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumConverter;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumListCellFatory;

public class PermissionNameViewSearchFields extends AbstractForm<PermissionName>
{

   private TextField name;

   @Inject
   @Bundle({ CrudKeys.class, PermissionName.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(PermissionActionEnum.class)
   private ResourceBundle actionBundle;

   @Inject
   private PermissionActionEnumConverter actionConverter;

   @Inject
   private PermissionActionEnumListCellFatory actionListCellFatory;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("PermissionName_name_description.title", "name", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(PermissionName model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());

   }

   public TextField getName()
   {
      return name;
   }
}
