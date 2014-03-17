package org.adorsys.adpharma.client.jpa.packagingmode;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.TextField;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

public class PackagingModeView extends AbstractForm<PackagingMode>
{

   private TextField name;

   @Inject
   @Bundle({ CrudKeys.class, PackagingMode.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("PackagingMode_name_description.title", "name", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<PackagingMode>> validate(PackagingMode model)
   {
      Set<ConstraintViolation<PackagingMode>> violations = new HashSet<ConstraintViolation<PackagingMode>>();
      return violations;
   }

   public void bind(PackagingMode model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
   }

   public TextField getName()
   {
      return name;
   }
}
