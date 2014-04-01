package org.adorsys.adpharma.client.jpa.customerinvoice;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceInsuranceEditController extends CustomerInvoiceInsuranceController
{

   @Inject
   CustomerInvoiceEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoice model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerInvoiceInsuranceSelection(), editView.getView().getCustomerInvoiceInsuranceForm());
      bind(editView.getView().getCustomerInvoiceInsuranceSelection(), editView.getView().getCustomerInvoiceInsuranceForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent CustomerInvoice p)
   {
      loadAssociation();
   }

}
