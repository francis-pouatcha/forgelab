package org.adorsys.adpharma.client.jpa.permissionname;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnum;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumConverter;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
