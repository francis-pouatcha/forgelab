package org.adorsys.adpharma.client.jpa.salesorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderItemArticleEditController extends SalesOrderItemArticleController
{

   @Inject
   SalesOrderItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrderItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderItemArticleSelection(), editView.getView().getSalesOrderItemArticleForm());
      bind(editView.getView().getSalesOrderItemArticleSelection(), editView.getView().getSalesOrderItemArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SalesOrderItem p)
   {
      loadAssociation();
   }

}
