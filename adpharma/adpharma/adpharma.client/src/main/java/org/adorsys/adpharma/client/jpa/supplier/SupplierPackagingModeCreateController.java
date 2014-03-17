package org.adorsys.adpharma.client.jpa.supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SupplierPackagingModeCreateController extends SupplierPackagingModeController
{

   @Inject
   SupplierCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Supplier model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getSupplierPackagingModeSelection());
      activateButton(createView.getView().getSupplierPackagingModeSelection());
   }
}
