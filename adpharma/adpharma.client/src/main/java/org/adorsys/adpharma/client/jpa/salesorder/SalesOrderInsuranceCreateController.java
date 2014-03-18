package org.adorsys.adpharma.client.jpa.salesorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SalesOrderInsuranceCreateController extends SalesOrderInsuranceController
{

   @Inject
   SalesOrderCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getSalesOrderInsuranceSelection(), createView.getView().getSalesOrderInsuranceForm());
      bind(createView.getView().getSalesOrderInsuranceSelection(), createView.getView().getSalesOrderInsuranceForm());
   }
}