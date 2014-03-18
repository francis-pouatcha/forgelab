package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import org.adorsys.javafx.crud.extensions.ViewModel;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductFamilyViewSearchFields extends AbstractForm<ProductFamily>
{

   private TextField name;

   private TextArea description;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("ProductFamily_name_description.title", "name", resourceBundle);
      description = viewBuilder.addTextArea("ProductFamily_description_description.title", "description", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductFamily model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());

   }

   public TextField getName()
   {
      return name;
   }

   public TextArea getDescription()
   {
      return description;
   }
}
