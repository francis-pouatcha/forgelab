package org.adorsys.adpharma.client.jpa.supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SupplierClearanceConfigEditController extends SupplierClearanceConfigController
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
      activateButton(editView.getView().getSupplierClearanceConfigSelection());
      bind(editView.getView().getSupplierClearanceConfigSelection());
   }
}
