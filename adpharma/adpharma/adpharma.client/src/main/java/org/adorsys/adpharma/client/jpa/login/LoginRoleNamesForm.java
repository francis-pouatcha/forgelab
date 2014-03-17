package org.adorsys.adpharma.client.jpa.login;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.rolename.RoleNamePermissionsForm;
import org.adorsys.adpharma.client.jpa.rolename.RoleNamePermissionsSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;

import org.adorsys.adpharma.client.jpa.rolename.RoleName;
import org.adorsys.adpharma.client.jpa.login.Login;

public class LoginRoleNamesForm extends AbstractToManyAssociation<Login, RoleName>
{

   private TableView<RoleName> dataList;
   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , RoleName.class
         , Login.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "name", "RoleName_name_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(Login model)
   {
   }

   public TableView<RoleName> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
