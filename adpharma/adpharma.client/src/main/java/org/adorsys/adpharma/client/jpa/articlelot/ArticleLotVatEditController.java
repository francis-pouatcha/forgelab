package org.adorsys.adpharma.client.jpa.articlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ArticleLotVatEditController extends ArticleLotVatController
{

   @Inject
   ArticleLotEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent ArticleLot model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getArticleLotVatSelection());
      bind(editView.getView().getArticleLotVatSelection(), editView.getView().getArticleLotVatForm());
   }
}
