package org.adorsys.adpharma.client.jpa.procurementorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProcurementOrderVatCreateController extends ProcurementOrderVatController
{

   @Inject
   ProcurementOrderCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ProcurementOrder model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getProcurementOrderVatSelection());
      activateButton(createView.getView().getProcurementOrderVatSelection());
   }
}
