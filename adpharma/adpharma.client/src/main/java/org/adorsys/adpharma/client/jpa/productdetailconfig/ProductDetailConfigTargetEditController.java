package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductDetailConfigTargetEditController extends ProductDetailConfigTargetController
{

   @Inject
   ProductDetailConfigEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent ProductDetailConfig model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getProductDetailConfigTargetSelection());
      bind(editView.getView().getProductDetailConfigTargetSelection(), editView.getView().getProductDetailConfigTargetForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent ProductDetailConfig p)
   {
//      loadAssociation();
   }

}
