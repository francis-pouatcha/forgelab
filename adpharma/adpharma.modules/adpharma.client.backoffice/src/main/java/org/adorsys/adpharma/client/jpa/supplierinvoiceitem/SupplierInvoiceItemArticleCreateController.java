package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SupplierInvoiceItemArticleCreateController extends SupplierInvoiceItemArticleController
{

   @Inject
   SupplierInvoiceItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent SupplierInvoiceItem model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getSupplierInvoiceItemArticleSelection(), createView.getView().getSupplierInvoiceItemArticleForm());
      activateButton(createView.getView().getSupplierInvoiceItemArticleSelection());
   }
}
