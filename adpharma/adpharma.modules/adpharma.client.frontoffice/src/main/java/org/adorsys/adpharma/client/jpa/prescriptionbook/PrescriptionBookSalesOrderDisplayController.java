package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PrescriptionBookSalesOrderDisplayController extends PrescriptionBookSalesOrderController
{

   @Inject
   private PrescriptionBookDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getPrescriptionBookSalesOrderSelection(), displayView.getView().getPrescriptionBookSalesOrderForm());
      bind(displayView.getView().getPrescriptionBookSalesOrderSelection(), displayView.getView().getPrescriptionBookSalesOrderForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent PrescriptionBook selectedEntity)
   {
      loadAssociation();
   }
}
