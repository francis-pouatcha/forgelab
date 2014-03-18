package org.adorsys.adpharma.client.jpa.productfamily;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProductFamilyParentFamillyCreateController extends ProductFamilyParentFamillyController
{

   @Inject
   ProductFamilyCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ProductFamily model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getProductFamilyParentFamillySelection());
      activateButton(createView.getView().getProductFamilyParentFamillySelection());
   }
}
