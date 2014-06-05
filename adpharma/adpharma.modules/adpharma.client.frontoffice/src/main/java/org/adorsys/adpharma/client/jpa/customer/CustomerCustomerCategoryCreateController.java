package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
@Singleton
public class CustomerCustomerCategoryCreateController extends CustomerCustomerCategoryController
{

   @Inject
   CustomerCreateView createView;
   
   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Customer model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getCustomerCustomerCategorySelection(), createView.getView().getCustomerCustomerCategoryForm());
      activateButton(createView.getView().getCustomerCustomerCategorySelection());
   }
}
