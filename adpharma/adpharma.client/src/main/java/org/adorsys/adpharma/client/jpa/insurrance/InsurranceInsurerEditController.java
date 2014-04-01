package org.adorsys.adpharma.client.jpa.insurrance;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InsurranceInsurerEditController extends InsurranceInsurerController
{

   @Inject
   InsurranceEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Insurrance model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getInsurranceInsurerSelection());
      bind(editView.getView().getInsurranceInsurerSelection(), editView.getView().getInsurranceInsurerForm());
   }
}
