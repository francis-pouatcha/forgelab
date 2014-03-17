package org.adorsys.adpharma.client.jpa.procurementorderitem;

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
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;

public class ProcurementOrderItemArticleSelection extends AbstractSelection<ProcurementOrderItem, Article>
{

   private ComboBox<Article> article;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      article = viewBuilder.addComboBox("ProcurementOrderItem_article_description.title", "article", resourceBundle, false);

      article.setCellFactory(new Callback<ListView<Article>, ListCell<Article>>()
      {
         @Override
         public ListCell<Article> call(ListView<Article> listView)
         {
            return new ProcurementOrderItemArticleListCell();
         }
      });
      article.setButtonCell(new ProcurementOrderItemArticleListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
   }

   public ComboBox<Article> getArticle()
   {
      return article;
   }
}
