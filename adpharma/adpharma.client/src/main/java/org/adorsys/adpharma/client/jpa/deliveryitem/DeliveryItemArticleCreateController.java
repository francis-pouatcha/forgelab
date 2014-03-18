package org.adorsys.adpharma.client.jpa.deliveryitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DeliveryItemArticleCreateController extends DeliveryItemArticleController
{

   @Inject
   DeliveryItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent DeliveryItem model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getDeliveryItemArticleSelection());
      activateButton(createView.getView().getDeliveryItemArticleSelection());
   }
}
