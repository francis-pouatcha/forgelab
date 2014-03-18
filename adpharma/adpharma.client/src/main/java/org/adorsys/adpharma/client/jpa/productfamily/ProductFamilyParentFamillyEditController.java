package org.adorsys.adpharma.client.jpa.productfamily;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductFamilyParentFamillyEditController extends ProductFamilyParentFamillyController
{

   @Inject
   ProductFamilyEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent ProductFamily model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getProductFamilyParentFamillySelection());
      bind(editView.getView().getProductFamilyParentFamillySelection());
   }
}
