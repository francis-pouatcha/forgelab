package org.adorsys.adpharma.client.jpa.person;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PersonLoginCreateController extends PersonLoginController
{

   @Inject
   PersonCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Person model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getPersonLoginSelection());
      activateButton(createView.getView().getPersonLoginSelection());
   }
}
