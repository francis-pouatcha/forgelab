package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceCustomerDisplayController extends CustomerInvoiceCustomerController
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
      disableButton(displayView.getView().getCustomerInvoiceCustomerSelection(), displayView.getView().getCustomerInvoiceCustomerForm());
      bind(displayView.getView().getCustomerInvoiceCustomerSelection(), displayView.getView().getCustomerInvoiceCustomerForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent CustomerInvoice selectedEntity)
   {
      loadAssociation();
   }
}
