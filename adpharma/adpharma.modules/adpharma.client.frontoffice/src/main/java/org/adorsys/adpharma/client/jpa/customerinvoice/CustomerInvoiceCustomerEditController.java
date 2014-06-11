package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceCustomerEditController extends CustomerInvoiceCustomerController
{

   @Inject
   CustomerInvoiceEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerInvoiceCustomerSelection(), editView.getView().getCustomerInvoiceCustomerForm());
      bind(editView.getView().getCustomerInvoiceCustomerSelection(), editView.getView().getCustomerInvoiceCustomerForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent CustomerInvoice p)
   {
      loadAssociation();
   }

}