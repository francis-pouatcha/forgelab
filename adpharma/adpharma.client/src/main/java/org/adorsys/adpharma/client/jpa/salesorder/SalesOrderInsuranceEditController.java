package org.adorsys.adpharma.client.jpa.salesorder;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SalesOrderInsuranceEditController extends SalesOrderInsuranceController
{

   @Inject
   SalesOrderEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SalesOrder model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSalesOrderInsuranceSelection(), editView.getView().getSalesOrderInsuranceForm());
      bind(editView.getView().getSalesOrderInsuranceSelection(), editView.getView().getSalesOrderInsuranceForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SalesOrder p)
   {
      loadAssociation();
   }

}
