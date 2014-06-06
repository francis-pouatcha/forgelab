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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleAgencySelection extends AbstractSelection<Article, Agency>
{

   private ComboBox<ArticleAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Article_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<ArticleAgency>, ListCell<ArticleAgency>>()
      {
         @Override
         public ListCell<ArticleAgency> call(ListView<ArticleAgency> listView)
         {
            return new ArticleAgencyListCell();
         }
      });
      agency.setButtonCell(new ArticleAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<ArticleAgency> getAgency()
   {
      return agency;
   }
}
