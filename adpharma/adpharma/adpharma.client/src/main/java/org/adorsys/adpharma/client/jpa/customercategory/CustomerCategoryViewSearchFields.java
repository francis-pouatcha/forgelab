package org.adorsys.adpharma.client.jpa.customercategory;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.TextArea;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCategoryViewSearchFields extends AbstractForm<CustomerCategory>
{

   private TextField name;

   private TextArea description;

   @Inject
   @Bundle({ CrudKeys.class, CustomerCategory.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("CustomerCategory_name_description.title", "name", resourceBundle);
      description = viewBuilder.addTextArea("CustomerCategory_description_description.title", "description", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerCategory model)
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
