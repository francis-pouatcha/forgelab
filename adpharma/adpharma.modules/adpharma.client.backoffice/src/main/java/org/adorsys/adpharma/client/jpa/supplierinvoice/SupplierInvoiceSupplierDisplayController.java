package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceSupplierDisplayController extends SupplierInvoiceSupplierController
{

   @Inject
   private SupplierInvoiceDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent SupplierInvoice model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getSupplierInvoiceSupplierSelection(), displayView.getView().getSupplierInvoiceSupplierForm());
      bind(displayView.getView().getSupplierInvoiceSupplierSelection(), displayView.getView().getSupplierInvoiceSupplierForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent SupplierInvoice selectedEntity)
   {
      loadAssociation();
   }
}
