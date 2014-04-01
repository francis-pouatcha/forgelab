package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderCashDrawerEditController extends SalesOrderCashDrawerController
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
      activateButton(editView.getView().getSalesOrderCashDrawerSelection());
      bind(editView.getView().getSalesOrderCashDrawerSelection(), editView.getView().getSalesOrderCashDrawerForm());
   }
}
