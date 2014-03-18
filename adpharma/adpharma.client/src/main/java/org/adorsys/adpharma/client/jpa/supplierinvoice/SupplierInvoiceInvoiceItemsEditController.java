package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceInvoiceItemsEditController extends SupplierInvoiceInvoiceItemsController
{

   @Inject
   SupplierInvoiceEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent SupplierInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSupplierInvoiceInvoiceItemsSelection(), editView.getView().getSupplierInvoiceInvoiceItemsForm());
      bind(editView.getView().getSupplierInvoiceInvoiceItemsSelection(), editView.getView().getSupplierInvoiceInvoiceItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent SupplierInvoice p)
   {
      loadAssociation();
   }
}
