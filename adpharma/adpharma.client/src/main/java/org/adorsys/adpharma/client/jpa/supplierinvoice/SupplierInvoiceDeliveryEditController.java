package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceDeliveryEditController extends SupplierInvoiceDeliveryController
{

   @Inject
   SupplierInvoiceEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SupplierInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSupplierInvoiceDeliverySelection(), editView.getView().getSupplierInvoiceDeliveryForm());
      bind(editView.getView().getSupplierInvoiceDeliverySelection(), editView.getView().getSupplierInvoiceDeliveryForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SupplierInvoice p)
   {
      loadAssociation();
   }

}
