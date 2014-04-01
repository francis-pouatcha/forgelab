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

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleClearanceConfigSelection extends AbstractSelection<Article, ClearanceConfig>
{

   private ComboBox<ArticleClearanceConfig> clearanceConfig;

   @Inject
   @Bundle({ CrudKeys.class, ClearanceConfig.class, Article.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      clearanceConfig = viewBuilder.addComboBox("Article_clearanceConfig_description.title", "clearanceConfig", resourceBundle, false);

      clearanceConfig.setCellFactory(new Callback<ListView<ArticleClearanceConfig>, ListCell<ArticleClearanceConfig>>()
      {
         @Override
         public ListCell<ArticleClearanceConfig> call(ListView<ArticleClearanceConfig> listView)
         {
            return new ArticleClearanceConfigListCell();
         }
      });
      clearanceConfig.setButtonCell(new ArticleClearanceConfigListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      clearanceConfig.valueProperty().bindBidirectional(model.clearanceConfigProperty());
   }

   public ComboBox<ArticleClearanceConfig> getClearanceConfig()
   {
      return clearanceConfig;
   }
}
