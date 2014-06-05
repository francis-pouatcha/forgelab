package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductDetailConfigTargetSelection  extends AbstractSelection<ProductDetailConfig, Article>
{

	   private ComboBox<ProductDetailConfigTarget> target;

	   @Inject
	   @Bundle({ CrudKeys.class, ProductDetailConfig.class, Article.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      target = viewBuilder.addComboBox("ProductDetailConfig_target_description.title", "target", resourceBundle, false);

	      target.setCellFactory(new Callback<ListView<ProductDetailConfigTarget>, ListCell<ProductDetailConfigTarget>>()
	      {
	         @Override
	         public ListCell<ProductDetailConfigTarget > call(ListView<ProductDetailConfigTarget> listView)
	         {
	            return new ProductDetailConfigTargetListCell();
	         }
	      });
	      target.setButtonCell(new ProductDetailConfigTargetListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(ProductDetailConfig model)
	   {
	      target.valueProperty().bindBidirectional(model.targetProperty());
	   }

	   public ComboBox<ProductDetailConfigTarget> getTarget()
	   {
	      return target;
	   }
}
