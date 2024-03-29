package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerInvoiceAgencyCreateController extends CustomerInvoiceAgencyController
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
      bind(createView.getView().getCustomerInvoiceAgencySelection(), createView.getView().getCustomerInvoiceAgencyForm());
      activateButton(createView.getView().getCustomerInvoiceAgencySelection());
   }
}
