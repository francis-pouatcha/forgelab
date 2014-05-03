package org.adorsys.adpharma.client.jpa.productfamily;

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
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductFamilyView extends AbstractForm<ProductFamily>
{

   private TextField name;

   @Inject
   private ProductFamilyParentFamilyForm productFamilyParentFamilyForm;
   @Inject
   private ProductFamilyParentFamilySelection productFamilyParentFamilySelection;

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
      viewBuilder.addTitlePane("ProductFamily_parentFamily_description.title", resourceBundle);
      viewBuilder.addSubForm("ProductFamily_parentFamily_description.title", "parentFamily", resourceBundle, productFamilyParentFamilyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProductFamily_parentFamily_description.title", "parentFamily", resourceBundle, productFamilyParentFamilySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProductFamily>(textInputControlValidator, name, ProductFamily.class, "name", resourceBundle));
   }

   public Set<ConstraintViolation<ProductFamily>> validate(ProductFamily model)
   {
      Set<ConstraintViolation<ProductFamily>> violations = new HashSet<ConstraintViolation<ProductFamily>>();
      violations.addAll(textInputControlValidator.validate(name, ProductFamily.class, "name", resourceBundle));
      return violations;
   }

   public void bind(ProductFamily model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      productFamilyParentFamilyForm.bind(model);
      productFamilyParentFamilySelection.bind(model);
   }

   public TextField getName()
   {
      return name;
   }

   public ProductFamilyParentFamilyForm getProductFamilyParentFamilyForm()
   {
      return productFamilyParentFamilyForm;
   }

   public ProductFamilyParentFamilySelection getProductFamilyParentFamilySelection()
   {
      return productFamilyParentFamilySelection;
   }
}
