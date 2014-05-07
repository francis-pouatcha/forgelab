package org.adorsys.adpharma.client.jpa.rolename;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class RoleNameViewSearchFields extends AbstractForm<RoleName>
{

   private TextField name;

   @Inject
   @Bundle({ CrudKeys.class, RoleName.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("RoleName_name_description.title", "name", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(RoleName model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());

   }

   public TextField getName()
   {
      return name;
   }
}
