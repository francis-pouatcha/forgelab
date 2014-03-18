package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceInvoiceItemsEditController extends CustomerInvoiceInvoiceItemsController
{

   @Inject
   CustomerInvoiceEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerInvoiceInvoiceItemsSelection(), editView.getView().getCustomerInvoiceInvoiceItemsForm());
      bind(editView.getView().getCustomerInvoiceInvoiceItemsSelection(), editView.getView().getCustomerInvoiceInvoiceItemsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent CustomerInvoice p)
   {
      loadAssociation();
   }
}
