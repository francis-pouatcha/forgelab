package org.adorsys.adpharma.client.jpa.articleequivalence;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticleEquivalenceEquivalentArticleCreateController extends ArticleEquivalenceEquivalentArticleController
{

   @Inject
   ArticleEquivalenceCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ArticleEquivalence model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getArticleEquivalenceEquivalentArticleSelection(), createView.getView().getArticleEquivalenceEquivalentArticleForm());
      bind(createView.getView().getArticleEquivalenceEquivalentArticleSelection(), createView.getView().getArticleEquivalenceEquivalentArticleForm());
   }
}
