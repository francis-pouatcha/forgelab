package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class CustomerEmployerCreateController extends CustomerEmployerController
{

   @Inject
   CustomerCreateView createView;
   
   @Inject
   ModalCustomerCreateView modalCustomerCreateView;
   
   

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Customer model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getCustomerEmployerSelection(), createView.getView().getCustomerEmployerForm());
      bind(modalCustomerCreateView.getView().getCustomerEmployerSelection(), modalCustomerCreateView.getView().getCustomerEmployerForm());
      activateButton(createView.getView().getCustomerEmployerSelection());
   }
}
