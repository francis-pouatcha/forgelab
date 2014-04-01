package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleAgencyEditController extends ArticleAgencyController
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
      activateButton(editView.getView().getArticleAgencySelection());
      bind(editView.getView().getArticleAgencySelection(), editView.getView().getArticleAgencyForm());
   }
}
