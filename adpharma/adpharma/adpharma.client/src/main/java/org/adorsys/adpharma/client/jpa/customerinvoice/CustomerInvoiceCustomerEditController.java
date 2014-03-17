package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceCustomerEditController extends CustomerInvoiceCustomerController
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
      activateButton(editView.getView().getCustomerInvoiceCustomerSelection());
      bind(editView.getView().getCustomerInvoiceCustomerSelection());
   }
}
