package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceDeliveryEditController extends SupplierInvoiceDeliveryController
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
      activateButton(editView.getView().getSupplierInvoiceDeliverySelection());
      bind(editView.getView().getSupplierInvoiceDeliverySelection());
   }
}
