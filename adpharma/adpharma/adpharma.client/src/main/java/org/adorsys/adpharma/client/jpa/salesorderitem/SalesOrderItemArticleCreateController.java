package org.adorsys.adpharma.client.jpa.salesorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SalesOrderItemArticleCreateController extends SalesOrderItemArticleController
{

   @Inject
   SalesOrderItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent SalesOrderItem model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getSalesOrderItemArticleSelection());
      activateButton(createView.getView().getSalesOrderItemArticleSelection());
   }
}
