package org.adorsys.adpharma.client.jpa.salesorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderItemArticleDisplayController extends SalesOrderItemArticleController
{

   @Inject
   private SalesOrderItemDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrderItem model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getSalesOrderItemArticleSelection(), displayView.getView().getSalesOrderItemArticleForm());
      bind(displayView.getView().getSalesOrderItemArticleSelection(), displayView.getView().getSalesOrderItemArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent SalesOrderItem selectedEntity)
   {
      loadAssociation();
   }
}
