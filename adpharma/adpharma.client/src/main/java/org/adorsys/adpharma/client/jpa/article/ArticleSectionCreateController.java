package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticleSectionCreateController extends ArticleSectionController
{

   @Inject
   ArticleCreateView createView;
   
   @Inject
   ModalArticleCreateView modalCreateView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Article model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getArticleSectionSelection(), createView.getView().getArticleSectionForm());
      bind(modalCreateView.getArticleView().getArticleSectionSelection(), modalCreateView.getArticleView().getArticleSectionForm());
      activateButton(createView.getView().getArticleSectionSelection());
   }
}
