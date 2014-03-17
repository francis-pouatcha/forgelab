package org.adorsys.adpharma.client.jpa.salesorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderItemArticleEditController extends SalesOrderItemArticleController
{

   @Inject
   SalesOrderItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrderItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderItemArticleSelection());
      bind(editView.getView().getSalesOrderItemArticleSelection());
   }
}
