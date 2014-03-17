package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextArea;
import org.adorsys.javafx.crud.extensions.ViewModel;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductFamilyParentFamillyForm extends AbstractToOneAssociation<ProductFamily, ProductFamily>
{

   private TextField code;

   private TextField name;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      code = viewBuilder.addTextField("ProductFamily_code_description.title", "code", resourceBundle);
      name = viewBuilder.addTextField("ProductFamily_name_description.title", "name", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductFamily model)
   {
      code.textProperty().bindBidirectional(model.getParentFamilly().codeProperty());
      name.textProperty().bindBidirectional(model.getParentFamilly().nameProperty());
   }

   public TextField getCode()
   {
      return code;
   }

   public TextField getName()
   {
      return name;
   }
}
