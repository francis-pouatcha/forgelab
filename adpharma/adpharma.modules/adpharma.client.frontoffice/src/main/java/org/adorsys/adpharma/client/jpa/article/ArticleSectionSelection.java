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

import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleSectionSelection extends AbstractSelection<Article, Section>
{

   private ComboBox<ArticleSection> section;

   @Inject
   @Bundle({ CrudKeys.class, Section.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      section = viewBuilder.addComboBox("Article_section_description.title", "section", resourceBundle, false);

      section.setCellFactory(new Callback<ListView<ArticleSection>, ListCell<ArticleSection>>()
      {
         @Override
         public ListCell<ArticleSection> call(ListView<ArticleSection> listView)
         {
            return new ArticleSectionListCell();
         }
      });
      section.setButtonCell(new ArticleSectionListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      section.valueProperty().bindBidirectional(model.sectionProperty());
   }

   public ComboBox<ArticleSection> getSection()
   {
      return section;
   }
}
