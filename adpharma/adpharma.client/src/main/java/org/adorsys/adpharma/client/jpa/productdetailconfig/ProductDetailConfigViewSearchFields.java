package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductDetailConfigViewSearchFields extends AbstractForm<ProductDetailConfig>
{

   private CheckBox active;

   @Inject
   @Bundle({ CrudKeys.class, ProductDetailConfig.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      active = viewBuilder.addCheckBox("ProductDetailConfig_active_description.title", "active", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductDetailConfig model)
   {
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());

   }

   public CheckBox getActive()
   {
      return active;
   }
}
