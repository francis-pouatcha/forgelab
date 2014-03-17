package org.adorsys.adpharma.client.jpa.rolename;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.rolename.RoleName;

public class RoleNameView extends AbstractForm<RoleName>
{

   private TextField name;

   @Inject
   private RoleNamePermissionsForm roleNamePermissionsForm;
   @Inject
   private RoleNamePermissionsSelection roleNamePermissionsSelection;

   @Inject
   @Bundle({ CrudKeys.class, RoleName.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("RoleName_name_description.title", "name", resourceBundle);
      viewBuilder.addTitlePane("RoleName_permissions_description.title", resourceBundle);
      viewBuilder.addSubForm("RoleName_permissions_description.title", "permissions", resourceBundle, roleNamePermissionsForm, ViewModel.READ_WRITE);
      viewBuilder.addSubForm("RoleName_permissions_description.title", "permissions", resourceBundle, roleNamePermissionsSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<RoleName>> validate(RoleName model)
   {
      Set<ConstraintViolation<RoleName>> violations = new HashSet<ConstraintViolation<RoleName>>();
      return violations;
   }

   public void bind(RoleName model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      roleNamePermissionsForm.bind(model);
      roleNamePermissionsSelection.bind(model);
   }

   public TextField getName()
   {
      return name;
   }

   public RoleNamePermissionsForm getRoleNamePermissionsForm()
   {
      return roleNamePermissionsForm;
   }

   public RoleNamePermissionsSelection getRoleNamePermissionsSelection()
   {
      return roleNamePermissionsSelection;
   }
}
