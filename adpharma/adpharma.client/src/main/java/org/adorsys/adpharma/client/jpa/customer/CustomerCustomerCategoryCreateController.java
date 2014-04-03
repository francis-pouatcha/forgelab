package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class CustomerCustomerCategoryCreateController extends CustomerCustomerCategoryController
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
      bind(createView.getView().getCustomerCustomerCategorySelection(), createView.getView().getCustomerCustomerCategoryForm());
      bind(modalCustomerCreateView.getView().getCustomerCustomerCategorySelection(), modalCustomerCreateView.getView().getCustomerCustomerCategoryForm());
      activateButton(createView.getView().getCustomerCustomerCategorySelection());
   }
}
