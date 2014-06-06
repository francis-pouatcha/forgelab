package org.adorsys.adpharma.client.jpa.permissionname;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnum;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumConverter;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnumListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PermissionNameView extends AbstractForm<PermissionName>
{

   private TextField name;

   private ComboBox<PermissionActionEnum> action;

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

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ComboBoxValidator comboBoxValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("PermissionName_name_description.title", "name", resourceBundle);
      action = viewBuilder.addComboBox("PermissionName_action_description.title", "action", resourceBundle, PermissionActionEnum.values());

      ComboBoxInitializer.initialize(action, actionConverter, actionListCellFatory, actionBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<PermissionName>(textInputControlValidator, name, PermissionName.class, "name", resourceBundle));
      action.valueProperty().addListener(new ComboBoxFoccusChangedListener<PermissionName, PermissionActionEnum>(comboBoxValidator, action, PermissionName.class, "action", resourceBundle));
   }

   public Set<ConstraintViolation<PermissionName>> validate(PermissionName model)
   {
      Set<ConstraintViolation<PermissionName>> violations = new HashSet<ConstraintViolation<PermissionName>>();
      violations.addAll(textInputControlValidator.validate(name, PermissionName.class, "name", resourceBundle));
      violations.addAll(comboBoxValidator.validate(action, PermissionName.class, "action", resourceBundle));
      return violations;
   }

   public void bind(PermissionName model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      action.valueProperty().bindBidirectional(model.actionProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public ComboBox<PermissionActionEnum> getAction()
   {
      return action;
   }
}
