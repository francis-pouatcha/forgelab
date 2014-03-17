package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SupplierInvoiceSupplierCreateController extends SupplierInvoiceSupplierController
{

   @Inject
   SupplierInvoiceCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent SupplierInvoice model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getSupplierInvoiceSupplierSelection());
      activateButton(createView.getView().getSupplierInvoiceSupplierSelection());
   }
}
