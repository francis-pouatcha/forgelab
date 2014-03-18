package org.adorsys.adpharma.client.jpa.procurementorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderProcurementOrderItemsDisplayController extends ProcurementOrderProcurementOrderItemsController
{

   @Inject
   private ProcurementOrderDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrder model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getProcurementOrderProcurementOrderItemsSelection(), displayView.getView().getProcurementOrderProcurementOrderItemsForm());
      bind(displayView.getView().getProcurementOrderProcurementOrderItemsSelection(), displayView.getView().getProcurementOrderProcurementOrderItemsForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ProcurementOrder selectedEntity)
   {
      loadAssociation();
   }
}
