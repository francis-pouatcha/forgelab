package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleFamilySelection extends AbstractSelection<Article, ProductFamily>
{

   private ComboBox<ProductFamily> family;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      family = viewBuilder.addComboBox("Article_family_description.title", "family", resourceBundle, false);

      family.setCellFactory(new Callback<ListView<ProductFamily>, ListCell<ProductFamily>>()
      {
         @Override
         public ListCell<ProductFamily> call(ListView<ProductFamily> listView)
         {
            return new ArticleFamilyListCell();
         }
      });
      family.setButtonCell(new ArticleFamilyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
   }

   public ComboBox<ProductFamily> getFamily()
   {
      return family;
   }
}
