package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderSalesOrderItemsEditController extends SalesOrderSalesOrderItemsController
{

   @Inject
   SalesOrderEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderSalesOrderItemsSelection(), editView.getView().getSalesOrderSalesOrderItemsForm());
      bind(editView.getView().getSalesOrderSalesOrderItemsSelection(), editView.getView().getSalesOrderSalesOrderItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent SalesOrder p)
   {
      loadAssociation();
   }
}
