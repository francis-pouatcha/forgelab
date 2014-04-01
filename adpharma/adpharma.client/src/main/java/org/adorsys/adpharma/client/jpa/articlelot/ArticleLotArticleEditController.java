package org.adorsys.adpharma.client.jpa.articlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleLotArticleEditController extends ArticleLotArticleController
{

   @Inject
   ArticleLotEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleLot model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getArticleLotArticleSelection(), editView.getView().getArticleLotArticleForm());
      bind(editView.getView().getArticleLotArticleSelection(), editView.getView().getArticleLotArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent ArticleLot p)
   {
      loadAssociation();
   }

}
