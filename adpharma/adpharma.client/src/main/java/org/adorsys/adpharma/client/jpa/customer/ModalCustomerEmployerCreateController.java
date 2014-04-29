package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ModalArticleCreateView;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ModalCustomerEmployerCreateController  extends CustomerEmployerController{

	 @Inject
	   ModalCustomerCreateView modalCreateView;

	   @PostConstruct
	   public void postConstruct()
	   {
	   }

	   public void handleNewModelEvent(@Observes @CreateModelEvent Customer model)
	   {
	      this.sourceEntity = model;
	      bind(modalCreateView.getView().getCustomerEmployerSelection(), modalCreateView.getView().getCustomerEmployerForm());
	      activateButton(modalCreateView.getView().getCustomerEmployerSelection());
	   }
}
