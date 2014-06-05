package org.adorsys.adpharma.client.jpa.articleequivalence;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleEquivalenceMainArticleDisplayController extends ArticleEquivalenceMainArticleController
{

   @Inject
   private ArticleEquivalenceDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleEquivalence model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getArticleEquivalenceMainArticleSelection(), displayView.getView().getArticleEquivalenceMainArticleForm());
      bind(displayView.getView().getArticleEquivalenceMainArticleSelection(), displayView.getView().getArticleEquivalenceMainArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ArticleEquivalence selectedEntity)
   {
      loadAssociation();
   }
}
