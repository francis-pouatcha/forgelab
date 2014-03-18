package org.adorsys.adpharma.client.jpa.articleequivalence;

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

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

public class ArticleEquivalenceMainArticleSelection extends AbstractSelection<ArticleEquivalence, Article>
{

   private ComboBox<Article> mainArticle;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, ArticleEquivalence.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      mainArticle = viewBuilder.addComboBox("ArticleEquivalence_mainArticle_description.title", "mainArticle", resourceBundle, false);

      mainArticle.setCellFactory(new Callback<ListView<Article>, ListCell<Article>>()
      {
         @Override
         public ListCell<Article> call(ListView<Article> listView)
         {
            return new ArticleEquivalenceMainArticleListCell();
         }
      });
      mainArticle.setButtonCell(new ArticleEquivalenceMainArticleListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ArticleEquivalence model)
   {
   }

   public ComboBox<Article> getMainArticle()
   {
      return mainArticle;
   }
}