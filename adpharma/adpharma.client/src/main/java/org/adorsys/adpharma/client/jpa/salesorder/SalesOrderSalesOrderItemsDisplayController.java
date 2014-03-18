package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderSalesOrderItemsDisplayController extends SalesOrderSalesOrderItemsController
{

   @Inject
   private SalesOrderDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getSalesOrderSalesOrderItemsSelection(), displayView.getView().getSalesOrderSalesOrderItemsForm());
      bind(displayView.getView().getSalesOrderSalesOrderItemsSelection(), displayView.getView().getSalesOrderSalesOrderItemsForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent SalesOrder selectedEntity)
   {
      loadAssociation();
   }
}
