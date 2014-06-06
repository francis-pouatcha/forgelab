package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderItemArticleSelection extends AbstractSelection<ProcurementOrderItem, Article>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "ProcurementOrderItem_article_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
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
