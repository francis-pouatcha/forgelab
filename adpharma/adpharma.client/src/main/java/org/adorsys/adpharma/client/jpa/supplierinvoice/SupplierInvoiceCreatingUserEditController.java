package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceCreatingUserEditController extends SupplierInvoiceCreatingUserController
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
      activateButton(editView.getView().getSupplierInvoiceCreatingUserSelection());
      bind(editView.getView().getSupplierInvoiceCreatingUserSelection(), editView.getView().getSupplierInvoiceCreatingUserForm());
   }
}
