package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;

public class ProductDetailConfigTargetSelection extends AbstractSelection<ProductDetailConfig, Article>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, ProductDetailConfig.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "ProductDetailConfig_target_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductDetailConfig model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getTarget()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
