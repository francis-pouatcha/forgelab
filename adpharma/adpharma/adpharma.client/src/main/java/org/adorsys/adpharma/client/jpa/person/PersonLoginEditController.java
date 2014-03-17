package org.adorsys.adpharma.client.jpa.person;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PersonLoginEditController extends PersonLoginController
{

   @Inject
   PersonEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Person model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPersonLoginSelection());
      bind(editView.getView().getPersonLoginSelection());
   }
}
