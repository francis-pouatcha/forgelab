package org.adorsys.adpharma.client.jpa.rolename;

import java.util.ResourceBundle;

import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumConverter;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class RoleNamePermissionsForm extends AbstractToManyAssociation<RoleName, PermissionName>
{

   private TableView<PermissionName> dataList;
   private Pagination pagination;

   @Inject
   private PermissionActionEnumConverter actionConverter;

   @Inject
   @Bundle({ CrudKeys.class
         , PermissionName.class
         , RoleName.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "name", "PermissionName_name_description.title", resourceBundle);
      viewBuilder.addEnumColumn(dataList, "action", "PermissionName_action_description.title", resourceBundle, actionConverter);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(RoleName model)
   {
   }

   public TableView<PermissionName> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
