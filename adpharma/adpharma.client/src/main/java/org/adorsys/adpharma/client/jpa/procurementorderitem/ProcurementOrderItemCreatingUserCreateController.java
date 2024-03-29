package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProcurementOrderItemCreatingUserCreateController extends ProcurementOrderItemCreatingUserController
{

   @Inject
   ProcurementOrderItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ProcurementOrderItem model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getProcurementOrderItemCreatingUserSelection(), createView.getView().getProcurementOrderItemCreatingUserForm());
      activateButton(createView.getView().getProcurementOrderItemCreatingUserSelection());
   }
}
