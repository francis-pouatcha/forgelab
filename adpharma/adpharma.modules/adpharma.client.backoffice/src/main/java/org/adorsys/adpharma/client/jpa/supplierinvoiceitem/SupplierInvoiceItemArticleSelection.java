package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

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

public class SupplierInvoiceItemArticleSelection extends AbstractSelection<SupplierInvoiceItem, Article>
{

   private ComboBox<SupplierInvoiceItemArticle> article;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, SupplierInvoiceItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      article = viewBuilder.addComboBox("SupplierInvoiceItem_article_description.title", "article", resourceBundle, false);

      article.setCellFactory(new Callback<ListView<SupplierInvoiceItemArticle>, ListCell<SupplierInvoiceItemArticle>>()
      {
         @Override
         public ListCell<SupplierInvoiceItemArticle> call(ListView<SupplierInvoiceItemArticle> listView)
         {
            return new SupplierInvoiceItemArticleListCell();
         }
      });
      article.setButtonCell(new SupplierInvoiceItemArticleListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoiceItem model)
   {
      article.valueProperty().bindBidirectional(model.articleProperty());
   }

   public ComboBox<SupplierInvoiceItemArticle> getArticle()
   {
      return article;
   }
}
