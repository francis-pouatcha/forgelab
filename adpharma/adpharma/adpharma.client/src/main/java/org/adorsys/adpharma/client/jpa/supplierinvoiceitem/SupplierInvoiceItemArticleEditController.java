package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierInvoiceItemArticleEditController extends SupplierInvoiceItemArticleController
{

   @Inject
   SupplierInvoiceItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent SupplierInvoiceItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSupplierInvoiceItemArticleSelection());
      bind(editView.getView().getSupplierInvoiceItemArticleSelection());
   }
}
