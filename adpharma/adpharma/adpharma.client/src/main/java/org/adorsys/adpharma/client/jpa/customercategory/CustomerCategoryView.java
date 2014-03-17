package org.adorsys.adpharma.client.jpa.customercategory;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCategoryView extends AbstractForm<CustomerCategory>
{

   private TextField name;

   private TextArea description;

   private BigDecimalField discountRate;

   @Inject
   @Bundle({ CrudKeys.class, CustomerCategory.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("CustomerCategory_name_description.title", "name", resourceBundle);
      description = viewBuilder.addTextArea("CustomerCategory_description_description.title", "description", resourceBundle);
      discountRate = viewBuilder.addBigDecimalField("CustomerCategory_discountRate_description.title", "discountRate", resourceBundle, NumberType.PERCENTAGE, locale);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<CustomerCategory>(textInputControlValidator, name, CustomerCategory.class, "name", resourceBundle));
      description.focusedProperty().addListener(new TextInputControlFoccusChangedListener<CustomerCategory>(textInputControlValidator, description, CustomerCategory.class, "description", resourceBundle));
   }

   public Set<ConstraintViolation<CustomerCategory>> validate(CustomerCategory model)
   {
      Set<ConstraintViolation<CustomerCategory>> violations = new HashSet<ConstraintViolation<CustomerCategory>>();
      violations.addAll(textInputControlValidator.validate(name, CustomerCategory.class, "name", resourceBundle));
      violations.addAll(textInputControlValidator.validate(description, CustomerCategory.class, "description", resourceBundle));
      return violations;
   }

   public void bind(CustomerCategory model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());
      discountRate.numberProperty().bindBidirectional(model.discountRateProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public TextArea getDescription()
   {
      return description;
   }

   public BigDecimalField getDiscountRate()
   {
      return discountRate;
   }
}
