package org.adorsys.adpharma.client.jpa.supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierPackagingModeEditController extends SupplierPackagingModeController
{

   @Inject
   SupplierEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Supplier model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSupplierPackagingModeSelection());
      bind(editView.getView().getSupplierPackagingModeSelection());
   }
}
