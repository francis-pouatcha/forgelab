package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerVoucherAgencyDisplayController extends CustomerVoucherAgencyController
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
      disableButton(displayView.getView().getCustomerVoucherAgencySelection());
      bind(displayView.getView().getCustomerVoucherAgencySelection(), displayView.getView().getCustomerVoucherAgencyForm());
   }
}
