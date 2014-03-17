package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceItemArticleEditController extends CustomerInvoiceItemArticleController
{

   @Inject
   CustomerInvoiceItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoiceItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerInvoiceItemArticleSelection());
      bind(editView.getView().getCustomerInvoiceItemArticleSelection());
   }
}
