package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticleDefaultSalesMarginCreateController extends ArticleDefaultSalesMarginController
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
      bind(createView.getView().getArticleDefaultSalesMarginSelection(), createView.getView().getArticleDefaultSalesMarginForm());
      activateButton(createView.getView().getArticleDefaultSalesMarginSelection());
   }
}
