package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerInvoiceSalesOrderCreateController extends CustomerInvoiceSalesOrderController
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
      bind(createView.getView().getCustomerInvoiceSalesOrderSelection());
      activateButton(createView.getView().getCustomerInvoiceSalesOrderSelection());
   }
}
