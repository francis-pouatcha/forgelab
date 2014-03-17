package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SupplierInvoiceInvoiceItemsCreateController extends SupplierInvoiceInvoiceItemsController
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
      disableButton(createView.getView().getSupplierInvoiceInvoiceItemsSelection(), createView.getView().getSupplierInvoiceInvoiceItemsForm());
      bind(createView.getView().getSupplierInvoiceInvoiceItemsSelection(), createView.getView().getSupplierInvoiceInvoiceItemsForm());
   }
}
