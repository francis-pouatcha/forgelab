package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryItemArticleSelection extends AbstractSelection<DeliveryItem, Article>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, DeliveryItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "DeliveryItem_article_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(DeliveryItem model)
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
