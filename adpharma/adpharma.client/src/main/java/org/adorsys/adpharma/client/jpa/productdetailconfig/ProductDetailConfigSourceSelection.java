package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.article.ArticleSectionListCell;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.section.Section;

public class ProductDetailConfigSourceSelection extends AbstractSelection<ProductDetailConfig, Article>
{

	   private ComboBox<ProductDetailConfigSource> source;

	   @Inject
	   @Bundle({ CrudKeys.class, ProductDetailConfig.class, Article.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      source = viewBuilder.addComboBox("ProductDetailConfig_source_description.title", "source", resourceBundle, false);

	      source.setCellFactory(new Callback<ListView<ProductDetailConfigSource>, ListCell<ProductDetailConfigSource>>()
	      {
	         @Override
	         public ListCell<ProductDetailConfigSource> call(ListView<ProductDetailConfigSource> listView)
	         {
	            return new ProductDetailConfigSourceListCell();
	         }
	      });
	      source.setButtonCell(new ProductDetailConfigSourceListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(ProductDetailConfig model)
	   {
	      source.valueProperty().bindBidirectional(model.sourceProperty());
	   }

	   public ComboBox<ProductDetailConfigSource> getSource()
	   {
	      return source;
	   }
}
