package org.adorsys.adpharma.client.jpa.productfamily;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductFamilyParentFamilyDisplayController extends ProductFamilyParentFamilyController
{

   @Inject
   private ProductFamilyDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ProductFamily model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getProductFamilyParentFamilySelection(), displayView.getView().getProductFamilyParentFamilyForm());
      bind(displayView.getView().getProductFamilyParentFamilySelection(), displayView.getView().getProductFamilyParentFamilyForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ProductFamily selectedEntity)
   {
      loadAssociation();
   }
}
