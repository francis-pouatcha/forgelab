package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProductDetailConfigSourceCreateController extends ProductDetailConfigSourceController
{

   @Inject
   ProductDetailConfigCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ProductDetailConfig model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getProductDetailConfigSourceSelection(), createView.getView().getProductDetailConfigSourceForm());
      bind(createView.getView().getProductDetailConfigSourceSelection(), createView.getView().getProductDetailConfigSourceForm());
   }
}