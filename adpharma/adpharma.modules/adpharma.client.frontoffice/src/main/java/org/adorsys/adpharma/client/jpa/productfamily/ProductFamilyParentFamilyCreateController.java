package org.adorsys.adpharma.client.jpa.productfamily;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProductFamilyParentFamilyCreateController extends ProductFamilyParentFamilyController
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
      disableButton(createView.getView().getProductFamilyParentFamilySelection(), createView.getView().getProductFamilyParentFamilyForm());
      bind(createView.getView().getProductFamilyParentFamilySelection(), createView.getView().getProductFamilyParentFamilyForm());
   }
}
