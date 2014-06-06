package org.adorsys.adpharma.client.jpa.productfamily;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductFamilyParentFamilyEditController extends ProductFamilyParentFamilyController
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
      activateButton(editView.getView().getProductFamilyParentFamilySelection(), editView.getView().getProductFamilyParentFamilyForm());
      bind(editView.getView().getProductFamilyParentFamilySelection(), editView.getView().getProductFamilyParentFamilyForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent ProductFamily p)
   {
      loadAssociation();
   }
}
