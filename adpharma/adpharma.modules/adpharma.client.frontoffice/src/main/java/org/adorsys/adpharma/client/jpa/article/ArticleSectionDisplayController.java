package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleSectionDisplayController extends ArticleSectionController
{

   @Inject
   private ArticleDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Article model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getArticleSectionSelection());
      bind(displayView.getView().getArticleSectionSelection(), displayView.getView().getArticleSectionForm());
   }
}
