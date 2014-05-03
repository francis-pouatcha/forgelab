package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PrescriptionBookSalesOrderEditController extends PrescriptionBookSalesOrderController
{

   @Inject
   PrescriptionBookEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPrescriptionBookSalesOrderSelection(), editView.getView().getPrescriptionBookSalesOrderForm());
      bind(editView.getView().getPrescriptionBookSalesOrderSelection(), editView.getView().getPrescriptionBookSalesOrderForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent PrescriptionBook p)
   {
      loadAssociation();
   }

}
