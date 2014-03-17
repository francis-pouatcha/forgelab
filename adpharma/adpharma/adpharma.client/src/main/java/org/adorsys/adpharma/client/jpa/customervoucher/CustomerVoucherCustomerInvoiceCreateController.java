package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerVoucherCustomerInvoiceCreateController extends CustomerVoucherCustomerInvoiceController
{

   @Inject
   CustomerVoucherCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent CustomerVoucher model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getCustomerVoucherCustomerInvoiceSelection());
      activateButton(createView.getView().getCustomerVoucherCustomerInvoiceSelection());
   }
}
