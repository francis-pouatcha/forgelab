package org.adorsys.adpharma.client.jpa.customervoucher;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerVoucherCustomerCreateController extends CustomerVoucherCustomerController
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
      activateButton(createView.getView().getCustomerVoucherCustomerSelection(), createView.getView().getCustomerVoucherCustomerForm());
      bind(createView.getView().getCustomerVoucherCustomerSelection(), createView.getView().getCustomerVoucherCustomerForm());
   }
}
