package org.adorsys.adpharma.client.jpa.supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SupplierAgencyCreateController extends SupplierAgencyController
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
      bind(createView.getView().getSupplierAgencySelection());
      activateButton(createView.getView().getSupplierAgencySelection());
   }
}
