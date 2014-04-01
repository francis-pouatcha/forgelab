package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class CustomerInvoiceItemArticleCreateController extends CustomerInvoiceItemArticleController
{

   @Inject
   CustomerInvoiceItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent CustomerInvoiceItem model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getCustomerInvoiceItemArticleSelection(), createView.getView().getCustomerInvoiceItemArticleForm());
      bind(createView.getView().getCustomerInvoiceItemArticleSelection(), createView.getView().getCustomerInvoiceItemArticleForm());
   }
}
