package org.adorsys.adpharma.client.jpa.articleequivalence;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleEquivalenceEquivalentArticleEditController extends ArticleEquivalenceEquivalentArticleController
{

   @Inject
   ArticleEquivalenceEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleEquivalence model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getArticleEquivalenceEquivalentArticleSelection());
      bind(editView.getView().getArticleEquivalenceEquivalentArticleSelection());
   }
}
