package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductDetailConfigSourceDisplayController extends ProductDetailConfigSourceController
{

   @Inject
   private ProductDetailConfigDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ProductDetailConfig model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getProductDetailConfigSourceSelection(), displayView.getView().getProductDetailConfigSourceForm());
      bind(displayView.getView().getProductDetailConfigSourceSelection(), displayView.getView().getProductDetailConfigSourceForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ProductDetailConfig selectedEntity)
   {
      loadAssociation();
   }
}
