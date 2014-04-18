package org.adorsys.adpharma.client.jpa.articlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleLotVatDisplayController extends ArticleLotVatController
{

   @Inject
   private ArticleLotDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleLot model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getArticleLotVatSelection());
      bind(displayView.getView().getArticleLotVatSelection(), displayView.getView().getArticleLotVatForm());
   }
}
