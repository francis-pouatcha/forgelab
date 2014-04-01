package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerInvoiceItemArticleDisplayController extends CustomerInvoiceItemArticleController
{

   @Inject
   private CustomerInvoiceItemDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent CustomerInvoiceItem model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getCustomerInvoiceItemArticleSelection(), displayView.getView().getCustomerInvoiceItemArticleForm());
      bind(displayView.getView().getCustomerInvoiceItemArticleSelection(), displayView.getView().getCustomerInvoiceItemArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent CustomerInvoiceItem selectedEntity)
   {
      loadAssociation();
   }
}
