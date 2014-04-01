package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class CustomerEmployerEditController extends CustomerEmployerController
{

   @Inject
   CustomerEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Customer model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getCustomerEmployerSelection());
      bind(editView.getView().getCustomerEmployerSelection(), editView.getView().getCustomerEmployerForm());
   }
}
