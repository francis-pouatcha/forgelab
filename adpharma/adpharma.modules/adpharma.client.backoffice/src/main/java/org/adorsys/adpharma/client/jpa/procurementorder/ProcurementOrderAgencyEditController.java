package org.adorsys.adpharma.client.jpa.procurementorder;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderAgencyEditController extends ProcurementOrderAgencyController
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
      activateButton(editView.getView().getProcurementOrderAgencySelection());
      bind(editView.getView().getProcurementOrderAgencySelection(), editView.getView().getProcurementOrderAgencyForm());
   }
}
