package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticlePackagingModeCreateController extends ArticlePackagingModeController
{

   @Inject
   ArticleCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Article model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getArticlePackagingModeSelection(), createView.getView().getArticlePackagingModeForm());
      activateButton(createView.getView().getArticlePackagingModeSelection());
   }
}
