package org.adorsys.adpharma.client.jpa.salesorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SalesOrderItemVatCreateController extends SalesOrderItemVatController
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
      bind(createView.getView().getSalesOrderItemVatSelection(), createView.getView().getSalesOrderItemVatForm());
      activateButton(createView.getView().getSalesOrderItemVatSelection());
   }
}
