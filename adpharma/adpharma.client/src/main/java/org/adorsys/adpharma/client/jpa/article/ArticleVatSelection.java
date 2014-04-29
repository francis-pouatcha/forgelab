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

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleVatSelection extends AbstractSelection<Article, VAT>
{

   private ComboBox<ArticleVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("Article_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<ArticleVat>, ListCell<ArticleVat>>()
      {
         @Override
         public ListCell<ArticleVat> call(ListView<ArticleVat> listView)
         {
            return new ArticleVatListCell();
         }
      });
      vat.setButtonCell(new ArticleVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<ArticleVat> getVat()
   {
      return vat;
   }
}
