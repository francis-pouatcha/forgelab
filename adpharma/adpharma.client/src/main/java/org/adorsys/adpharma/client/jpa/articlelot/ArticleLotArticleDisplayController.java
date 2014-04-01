package org.adorsys.adpharma.client.jpa.articlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleLotArticleDisplayController extends ArticleLotArticleController
{

   @Inject
   private ArticleLotDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleLot model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getArticleLotArticleSelection(), displayView.getView().getArticleLotArticleForm());
      bind(displayView.getView().getArticleLotArticleSelection(), displayView.getView().getArticleLotArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ArticleLot selectedEntity)
   {
      loadAssociation();
   }
}
