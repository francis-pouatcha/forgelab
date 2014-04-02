package org.adorsys.adpharma.client.jpa.article;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ArticleAgencyCreateController extends ArticleAgencyController
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
      bind(createView.getView().getArticleAgencySelection(), createView.getView().getArticleAgencyForm());
      bind(modalCreateView.getView().getArticleAgencySelection(), modalCreateView.getView().getArticleAgencyForm());
      activateButton(createView.getView().getArticleAgencySelection());
   }
}
