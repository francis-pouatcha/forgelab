package org.adorsys.adpharma.client.jpa.articleequivalence;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleEquivalenceMainArticleEditController extends ArticleEquivalenceMainArticleController
{

   @Inject
   ArticleEquivalenceEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleEquivalence model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getArticleEquivalenceMainArticleSelection(), editView.getView().getArticleEquivalenceMainArticleForm());
      bind(editView.getView().getArticleEquivalenceMainArticleSelection(), editView.getView().getArticleEquivalenceMainArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent ArticleEquivalence p)
   {
      loadAssociation();
   }

}
