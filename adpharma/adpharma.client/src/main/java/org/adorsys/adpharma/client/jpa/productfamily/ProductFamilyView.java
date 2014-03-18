package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductFamilyView extends AbstractForm<ProductFamily>
{

   private TextField name;

   private TextArea description;

   @Inject
   private ProductFamilyParentFamillyForm productFamilyParentFamillyForm;
   @Inject
   private ProductFamilyParentFamillySelection productFamilyParentFamillySelection;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("ProductFamily_name_description.title", "name", resourceBundle);
      description = viewBuilder.addTextArea("ProductFamily_description_description.title", "description", resourceBundle);
      viewBuilder.addTitlePane("ProductFamily_parentFamilly_description.title", resourceBundle);
      viewBuilder.addSubForm("ProductFamily_parentFamilly_description.title", "parentFamilly", resourceBundle, productFamilyParentFamillyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProductFamily_parentFamilly_description.title", "parentFamilly", resourceBundle, productFamilyParentFamillySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProductFamily>(textInputControlValidator, name, ProductFamily.class, "name", resourceBundle));
      description.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProductFamily>(textInputControlValidator, description, ProductFamily.class, "description", resourceBundle));
   }

   public Set<ConstraintViolation<ProductFamily>> validate(ProductFamily model)
   {
      Set<ConstraintViolation<ProductFamily>> violations = new HashSet<ConstraintViolation<ProductFamily>>();
      violations.addAll(textInputControlValidator.validate(name, ProductFamily.class, "name", resourceBundle));
      violations.addAll(textInputControlValidator.validate(description, ProductFamily.class, "description", resourceBundle));
      return violations;
   }

   public void bind(ProductFamily model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      description.textProperty().bindBidirectional(model.descriptionProperty());
      productFamilyParentFamillyForm.bind(model);
      productFamilyParentFamillySelection.bind(model);
   }

   public TextField getName()
   {
      return name;
   }

   public TextArea getDescription()
   {
      return description;
   }

   public ProductFamilyParentFamillyForm getProductFamilyParentFamillyForm()
   {
      return productFamilyParentFamillyForm;
   }

   public ProductFamilyParentFamillySelection getProductFamilyParentFamillySelection()
   {
      return productFamilyParentFamillySelection;
   }
}
