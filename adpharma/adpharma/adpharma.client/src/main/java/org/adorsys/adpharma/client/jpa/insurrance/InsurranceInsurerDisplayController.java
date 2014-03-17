package org.adorsys.adpharma.client.jpa.insurrance;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class InsurranceInsurerDisplayController extends InsurranceInsurerController
{

   @Inject
   private InsurranceDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Insurrance model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getInsurranceInsurerSelection());
   }
}
