package org.adorsys.adpharma.client.jpa.login;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class LoginRoleNamesCreateController extends LoginRoleNamesController
{

   @Inject
   LoginCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Login model)
   {
      this.sourceEntity = model;
      disableButton(createView.getView().getLoginRoleNamesSelection(), createView.getView().getLoginRoleNamesForm());
      bind(createView.getView().getLoginRoleNamesSelection(), createView.getView().getLoginRoleNamesForm());
   }
}
