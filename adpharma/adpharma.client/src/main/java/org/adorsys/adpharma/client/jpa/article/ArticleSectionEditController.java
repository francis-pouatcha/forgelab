package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleSectionEditController extends ArticleSectionController
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
      activateButton(editView.getView().getArticleSectionSelection());
      bind(editView.getView().getArticleSectionSelection(), editView.getView().getArticleSectionForm());
   }
}
