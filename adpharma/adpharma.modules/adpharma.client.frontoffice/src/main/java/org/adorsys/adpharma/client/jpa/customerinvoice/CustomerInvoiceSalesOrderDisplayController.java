package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceSalesOrderDisplayController extends CustomerInvoiceSalesOrderController
{

   @Inject
   private CustomerInvoiceDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoice model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getCustomerInvoiceSalesOrderSelection());
      bind(displayView.getView().getCustomerInvoiceSalesOrderSelection(), displayView.getView().getCustomerInvoiceSalesOrderForm());
   }
}
