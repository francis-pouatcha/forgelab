package org.adorsys.adpharma.client.jpa.articlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticleLotArticleCreateController extends ArticleLotArticleController
{

   @Inject
   ArticleLotCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ArticleLot model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getArticleLotArticleSelection());
      activateButton(createView.getView().getArticleLotArticleSelection());
   }
}
