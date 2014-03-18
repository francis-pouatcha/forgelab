package org.adorsys.adpharma.client.jpa.procurementorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderProcurementOrderItemsEditController extends ProcurementOrderProcurementOrderItemsController
{

   @Inject
   ProcurementOrderEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrder model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getProcurementOrderProcurementOrderItemsSelection(), editView.getView().getProcurementOrderProcurementOrderItemsForm());
      bind(editView.getView().getProcurementOrderProcurementOrderItemsSelection(), editView.getView().getProcurementOrderProcurementOrderItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent ProcurementOrder p)
   {
      loadAssociation();
   }
}
