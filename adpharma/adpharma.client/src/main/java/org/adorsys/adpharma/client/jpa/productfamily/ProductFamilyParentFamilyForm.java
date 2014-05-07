package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductFamilyParentFamilyForm extends AbstractToOneAssociation<ProductFamily, ProductFamily>
{

   private TextField name;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("ProductFamily_name_description.title", "name", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductFamily model)
   {
      name.textProperty().bindBidirectional(model.getParentFamily().nameProperty());
   }

   public void update(ProductFamilyParentFamily data)
   {
      name.textProperty().set(data.nameProperty().get());
   }

   public TextField getName()
   {
      return name;
   }
}
