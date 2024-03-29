package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherCustomerInvoiceDisplayController extends CustomerVoucherCustomerInvoiceController
{

   @Inject
   private CustomerVoucherDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerVoucher model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getCustomerVoucherCustomerInvoiceSelection(), displayView.getView().getCustomerVoucherCustomerInvoiceForm());
      bind(displayView.getView().getCustomerVoucherCustomerInvoiceSelection(), displayView.getView().getCustomerVoucherCustomerInvoiceForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent CustomerVoucher selectedEntity)
   {
      loadAssociation();
   }
}
