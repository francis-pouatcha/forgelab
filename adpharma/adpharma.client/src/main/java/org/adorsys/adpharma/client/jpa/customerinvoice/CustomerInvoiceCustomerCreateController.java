package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerInvoiceCustomerCreateController extends CustomerInvoiceCustomerController
{

   @Inject
   CustomerInvoiceCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent CustomerInvoice model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getCustomerInvoiceCustomerSelection(), createView.getView().getCustomerInvoiceCustomerForm());
      bind(createView.getView().getCustomerInvoiceCustomerSelection(), createView.getView().getCustomerInvoiceCustomerForm());
   }
}
