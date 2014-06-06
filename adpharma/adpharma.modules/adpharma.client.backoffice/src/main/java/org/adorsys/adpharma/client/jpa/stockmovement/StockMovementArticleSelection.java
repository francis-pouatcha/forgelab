package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class StockMovementArticleSelection extends AbstractSelection<StockMovement, Article>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, StockMovement.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "StockMovement_article_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(StockMovement model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getArticle()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
