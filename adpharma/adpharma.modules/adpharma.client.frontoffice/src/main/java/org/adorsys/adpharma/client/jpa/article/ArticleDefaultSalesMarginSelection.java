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

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleDefaultSalesMarginSelection extends AbstractSelection<Article, SalesMargin>
{

   private ComboBox<ArticleDefaultSalesMargin> defaultSalesMargin;

   @Inject
   @Bundle({ CrudKeys.class, SalesMargin.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      defaultSalesMargin = viewBuilder.addComboBox("Article_defaultSalesMargin_description.title", "defaultSalesMargin", resourceBundle, false);

      defaultSalesMargin.setCellFactory(new Callback<ListView<ArticleDefaultSalesMargin>, ListCell<ArticleDefaultSalesMargin>>()
      {
         @Override
         public ListCell<ArticleDefaultSalesMargin> call(ListView<ArticleDefaultSalesMargin> listView)
         {
            return new ArticleDefaultSalesMarginListCell();
         }
      });
      defaultSalesMargin.setButtonCell(new ArticleDefaultSalesMarginListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      defaultSalesMargin.valueProperty().bindBidirectional(model.defaultSalesMarginProperty());
   }

   public ComboBox<ArticleDefaultSalesMargin> getDefaultSalesMargin()
   {
      return defaultSalesMargin;
   }
}
