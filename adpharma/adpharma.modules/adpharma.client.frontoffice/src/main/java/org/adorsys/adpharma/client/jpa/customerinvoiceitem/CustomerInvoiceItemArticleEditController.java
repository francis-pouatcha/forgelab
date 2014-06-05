package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceItemArticleEditController extends CustomerInvoiceItemArticleController
{

   @Inject
   CustomerInvoiceItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoiceItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerInvoiceItemArticleSelection(), editView.getView().getCustomerInvoiceItemArticleForm());
      bind(editView.getView().getCustomerInvoiceItemArticleSelection(), editView.getView().getCustomerInvoiceItemArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent CustomerInvoiceItem p)
   {
      loadAssociation();
   }

}
