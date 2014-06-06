package org.adorsys.adpharma.client.jpa.rolename;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
