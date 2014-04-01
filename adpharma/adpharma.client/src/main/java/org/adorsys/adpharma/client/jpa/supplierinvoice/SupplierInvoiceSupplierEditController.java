package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceSupplierEditController extends SupplierInvoiceSupplierController
{

   @Inject
   SupplierInvoiceEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent SupplierInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSupplierInvoiceSupplierSelection(), editView.getView().getSupplierInvoiceSupplierForm());
      bind(editView.getView().getSupplierInvoiceSupplierSelection(), editView.getView().getSupplierInvoiceSupplierForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent SupplierInvoice p)
   {
      loadAssociation();
   }

}
