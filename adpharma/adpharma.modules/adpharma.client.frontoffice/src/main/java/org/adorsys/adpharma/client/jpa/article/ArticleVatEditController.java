package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleVatEditController extends ArticleVatController
{

   @Inject
   ArticleEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Article model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getArticleVatSelection());
      bind(editView.getView().getArticleVatSelection(), editView.getView().getArticleVatForm());
   }
}
