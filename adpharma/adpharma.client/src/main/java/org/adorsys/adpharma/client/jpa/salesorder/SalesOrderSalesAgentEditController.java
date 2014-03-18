package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderSalesAgentEditController extends SalesOrderSalesAgentController
{

   @Inject
   SalesOrderEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderSalesAgentSelection(), editView.getView().getSalesOrderSalesAgentForm());
      bind(editView.getView().getSalesOrderSalesAgentSelection(), editView.getView().getSalesOrderSalesAgentForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SalesOrder p)
   {
      loadAssociation();
   }

}
