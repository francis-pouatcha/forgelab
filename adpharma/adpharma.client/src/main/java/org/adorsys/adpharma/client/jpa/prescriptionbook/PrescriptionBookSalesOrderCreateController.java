package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PrescriptionBookSalesOrderCreateController extends PrescriptionBookSalesOrderController
{

   @Inject
   PrescriptionBookCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getPrescriptionBookSalesOrderSelection(), createView.getView().getPrescriptionBookSalesOrderForm());
      bind(createView.getView().getPrescriptionBookSalesOrderSelection(), createView.getView().getPrescriptionBookSalesOrderForm());
   }
}
