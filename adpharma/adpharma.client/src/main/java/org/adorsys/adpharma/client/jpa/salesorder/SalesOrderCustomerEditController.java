package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderCustomerEditController extends SalesOrderCustomerController
{

   @Inject
   SalesOrderEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderCustomerSelection(), editView.getView().getSalesOrderCustomerForm());
      bind(editView.getView().getSalesOrderCustomerSelection(), editView.getView().getSalesOrderCustomerForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SalesOrder p)
   {
      loadAssociation();
   }

}
